package com.demo.test.Services;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Service
public class CaptchaService {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 50;
    private static final int LENGTH = 6;
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public CaptchaResult generateCaptcha() {
        // Generate random text
        String captchaText = generateRandomText();

        // Create image
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Set background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Add noise
        addNoise(g);

        // Draw text
        drawText(g, captchaText);

        // Convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CAPTCHA image", e);
        }

        return new CaptchaResult(captchaText, baos.toByteArray());
    }

    private String generateRandomText() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private void addNoise(Graphics2D g) {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawText(Graphics2D g, String text) {
        Random random = new Random();
        for (int i = 0; i < text.length(); i++) {
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            g.setFont(new Font("Arial", Font.BOLD, 30 + random.nextInt(5)));
            g.drawString(String.valueOf(text.charAt(i)), 25 * i + 10, 35 + random.nextInt(5));
        }
    }

    @Getter
    public static class CaptchaResult {
        // Getters
        private final String text;
        private final byte[] imageData;

        public CaptchaResult(String text, byte[] imageData) {
            this.text = text;
            this.imageData = imageData;
        }

    }
}