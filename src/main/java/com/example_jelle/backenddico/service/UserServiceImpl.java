package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.dto.UserOutputDto;
import com.example_jelle.backenddico.dto.onboarding.DeviceDto;
import com.example_jelle.backenddico.dto.onboarding.OnboardingRequestDto;
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
    // private final EmailService emailService; // Klaar voor toekomstige implementatie

    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, UserDeviceRepository userDeviceRepository, PasswordEncoder passwordEncoder /*, EmailService emailService */) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userDeviceRepository = userDeviceRepository;
        this.passwordEncoder = passwordEncoder;
        // this.emailService = emailService;
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
        user.setEnabled(false); // Account is pas actief na verificatie

        // De User constructor initialiseert de flags al correct (alles op false).

        // Genereer een 6-cijferige code
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
        user.setVerificationCode(code);
        user.setVerificationCodeExpires(LocalDateTime.now().plusHours(1)); // Code is 1 uur geldig

        logger.info("DEV MODE: Verification code for {} is {}", user.getEmail(), code);
        // In een productieomgeving zou hier de e-mail verstuurd worden.
        // emailService.sendVerificationEmail(user.getEmail(), code);

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
    @Transactional
    public UserOutputDto saveProfileDetails(String userEmail, OnboardingRequestDto onboardingData) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden voor onboarding."));

        // --- Verwerk gebruikersvoorkeuren ---
        if (onboardingData.getPreferences() != null) {
            PreferencesDto preferences = onboardingData.getPreferences();

            UserProfile profile = userProfileRepository.findById(user.getId()).orElse(new UserProfile());
            if (profile.getUser() == null) {
                profile.setUser(user);
            }

            profile.setMeasurementSystem(preferences.getSystem());
            // FIX: Converteer de Gender Enum naar een String voor de database.
            profile.setGender(preferences.getGender().name());

            // FIX: De DTO bevat nu Doubles. De validatie is al gebeurd, dus we kunnen direct toewijzen.
            // De oude try-catch en StringUtils.hasText() zijn niet meer nodig.
            profile.setWeight(preferences.getWeight());
            profile.setHeight(preferences.getHeight());
            profile.setBmi(preferences.getBmi());

            userProfileRepository.save(profile);
            user.getFlags().setHasPreferences(true); // Zet de flag
        }

        // --- Verwerk diabetische apparaten ---
        if (onboardingData.getDiabeticDevices() != null && !onboardingData.getDiabeticDevices().isEmpty()) {
            // Optioneel: verwijder oude apparaten voordat je nieuwe toevoegt
            // userDeviceRepository.deleteAllByUser(user);

            for (DeviceDto deviceDto : onboardingData.getDiabeticDevices()) {
                UserDevice userDevice = new UserDevice();
                userDevice.setUser(user);
                // FIX: Converteer de DeviceCategory Enum naar een String.
                userDevice.setCategory(deviceDto.getCategorie().name());
                userDevice.setManufacturer(deviceDto.getFabrikant());
                userDevice.setModel(deviceDto.getModel());
                userDeviceRepository.save(userDevice);
            }
            // In de specificaties is er een aparte flag voor devices, die kan hier worden gezet.
            // user.getFlags().setHasDevices(true);
        }

        // Volgens de specificaties wordt deze methode aangeroepen na de /register-details pagina.
        user.getFlags().setHasDetails(true);

        User savedUser = userRepository.save(user);
        logger.info("Profile details saved and flags updated for user: {}", userEmail);
        return UserOutputDto.from(savedUser);
    }
}