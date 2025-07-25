package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.FullUserProfileDto;
import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.onboarding.DeviceDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
import com.example_jelle.backenddico.dto.onboarding.MedicineInfoDto;
import com.example_jelle.backenddico.dto.onboarding.PreferencesDto;
import com.example_jelle.backenddico.exceptions.EmailAlreadyExists;
import com.example_jelle.backenddico.exceptions.InvalidTokenException;
import com.example_jelle.backenddico.exceptions.UserNotFoundException;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.model.UserDevice;
import com.example_jelle.backenddico.model.UserProfile;
import com.example_jelle.backenddico.payload.request.RegisterRequest;
import com.example_jelle.backenddico.repository.UserDeviceRepository;
import com.example_jelle.backenddico.repository.UserProfileRepository;
import com.example_jelle.backenddico.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, UserDeviceRepository userDeviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExists("E-mailadres '" + req.getEmail() + "' is al in gebruik.");
        }
        User user = new User();
        user.setUsername(req.getEmail());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());
        user.setRole("USER");
        user.setEnabled(false);
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
        user.setVerificationCode(code);
        user.setVerificationCodeExpires(LocalDateTime.now().plusHours(1));
        logger.info("DEV MODE: Verification code for {} is {}", user.getEmail(), code);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User verifyUser(String email, String token) {
        logger.info("Attempting to verify email {} with code: '{}'", email, token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidTokenException("Verificatiecode is ongeldig of verlopen."));
        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(token) || user.getVerificationCodeExpires().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Verificatiecode is ongeldig of verlopen.");
        }
        user.setEnabled(true);
        user.getFlags().setEmailVerified(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpires(null);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserOutputDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker met e-mail '" + email + "' niet gevonden."));
        return UserOutputDto.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public FullUserProfileDto getFullUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker met e-mail '" + email + "' niet gevonden."));
        // De .from() methode in de DTO handelt de volledige mapping af.
        return FullUserProfileDto.from(user);
    }

    @Override
    @Transactional
    public FullUserProfileDto saveProfileDetails(String userEmail, OnboardingRequestDto onboardingData) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden voor onboarding."));

        // Haal het profiel eenmalig op of maak een nieuw profiel aan.
        // Dit voorkomt onnodige database-queries.
        UserProfile profile = user.getUserProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
        }

        if (onboardingData.getPreferences() != null) {
            PreferencesDto preferences = onboardingData.getPreferences();
            profile.setMeasurementSystem(preferences.getSystem());
            profile.setGender(preferences.getGender().name());
            profile.setWeight(preferences.getWeight());
            profile.setHeight(preferences.getHeight());
            profile.setBmi(preferences.getBmi());
            userProfileRepository.save(profile);
            user.getFlags().setHasPreferences(true);
        }

        // Verwerk de nieuwe medicatiegegevens
        if (onboardingData.getMedicineInfo() != null) {
            MedicineInfoDto medicineInfo = onboardingData.getMedicineInfo();
            profile.setDiabetesType(medicineInfo.getDiabetesType());
            profile.setLongActingInsulin(medicineInfo.getLongActingInsulin());
            profile.setShortActingInsulin(medicineInfo.getShortActingInsulin());
        }

        userProfileRepository.save(profile); // Sla alle profielwijzigingen in één keer op

        if (onboardingData.getDiabeticDevices() != null && !onboardingData.getDiabeticDevices().isEmpty()) {
            userDeviceRepository.deleteAllByUser(user); // Verwijder oude apparaten voor een schone lei
            for (DeviceDto deviceDto : onboardingData.getDiabeticDevices()) {
                UserDevice userDevice = new UserDevice();
                userDevice.setUser(user);
                userDevice.setCategory(deviceDto.getCategorie().name());
                userDevice.setManufacturer(deviceDto.getFabrikant());
                userDevice.setModel(deviceDto.getModel());
                userDeviceRepository.save(userDevice);
            }
        }

        user.getFlags().setHasDetails(true);
        User savedUser = userRepository.save(user); // Sla de gebruiker op om de vlaggen te persisteren
        logger.info("Profile details saved and flags updated for user: {}", userEmail);

        // Retourneer de volledige, bijgewerkte DTO voor een consistente response.
        return FullUserProfileDto.from(savedUser);
    }
}