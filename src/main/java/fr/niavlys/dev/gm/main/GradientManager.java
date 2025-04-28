package fr.niavlys.dev.gm.main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GradientManager {

    /**
     * Crée un texte avec un dégradé entre deux couleurs
     */
    public static BaseComponent[] createGradient(String text, Color start, Color end) {
        ComponentBuilder builder = new ComponentBuilder();
        List<Color> gradient = createGradientColors(start, end, text.length());

        for (int i = 0; i < text.length(); i++) {
            builder.append(String.valueOf(text.charAt(i)))
                    .color(ChatColor.of(new java.awt.Color(
                            gradient.get(i).getRed(),
                            gradient.get(i).getGreen(),
                            gradient.get(i).getBlue()
                    )));
        }

        return builder.create();
    }

    /**
     * Crée un texte avec un dégradé pour les titres d'inventaire
     */
    public static String createGradientTitle(String text, Color start, Color end) {
        StringBuilder result = new StringBuilder();
        List<Color> gradient = createGradientColors(start, end, text.length());

        for (int i = 0; i < text.length(); i++) {
            result.append(ChatColor.of(new java.awt.Color(
                    gradient.get(i).getRed(),
                    gradient.get(i).getGreen(),
                    gradient.get(i).getBlue()
            ))).append(text.charAt(i));
        }

        return result.toString();
    }

    /**
     * Crée un texte avec un dégradé utilisant plusieurs couleurs
     */
    public static BaseComponent[] createMultiGradient(String text, Color... colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("Il faut au moins deux couleurs pour créer un dégradé");
        }

        ComponentBuilder builder = new ComponentBuilder();
        int textLength = text.length();
        int segments = colors.length - 1;
        int charsPerSegment = textLength / segments;

        for (int segment = 0; segment < segments; segment++) {
            Color startColor = colors[segment];
            Color endColor = colors[segment + 1];

            int startIndex = segment * charsPerSegment;
            int endIndex = (segment == segments - 1) ? textLength : (segment + 1) * charsPerSegment;

            String subText = text.substring(startIndex, endIndex);
            List<Color> gradientColors = createGradientColors(startColor, endColor, endIndex - startIndex);

            for (int i = 0; i < subText.length(); i++) {
                builder.append(String.valueOf(subText.charAt(i)))
                        .color(ChatColor.of(new java.awt.Color(
                                gradientColors.get(i).getRed(),
                                gradientColors.get(i).getGreen(),
                                gradientColors.get(i).getBlue()
                        )));
            }
        }

        return builder.create();
    }

    /**
     * Crée un texte avec un dégradé utilisant plusieurs couleurs, retourne une String
     */
    public static String createMultiGradientTitle(String text, Color... colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("Il faut au moins deux couleurs pour créer un dégradé");
        }

        StringBuilder result = new StringBuilder();
        int textLength = text.length();
        int segments = colors.length - 1;
        int charsPerSegment = textLength / segments;

        for (int segment = 0; segment < segments; segment++) {
            Color startColor = colors[segment];
            Color endColor = colors[segment + 1];

            int startIndex = segment * charsPerSegment;
            int endIndex = (segment == segments - 1) ? textLength : (segment + 1) * charsPerSegment;

            String subText = text.substring(startIndex, endIndex);
            List<Color> gradientColors = createGradientColors(startColor, endColor, endIndex - startIndex);

            for (int i = 0; i < subText.length(); i++) {
                result.append(ChatColor.of(new java.awt.Color(
                        gradientColors.get(i).getRed(),
                        gradientColors.get(i).getGreen(),
                        gradientColors.get(i).getBlue()
                ))).append(subText.charAt(i));
            }
        }

        return result.toString();
    }

    /**
     * Crée un dégradé de couleurs entre deux couleurs
     */
    private static List<Color> createGradientColors(Color start, Color end, int steps) {
        List<Color> colors = new ArrayList<>();
        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (steps - 1);
            int red = (int) (start.getRed() * (1 - ratio) + end.getRed() * ratio);
            int green = (int) (start.getGreen() * (1 - ratio) + end.getGreen() * ratio);
            int blue = (int) (start.getBlue() * (1 - ratio) + end.getBlue() * ratio);
            colors.add(new Color(red, green, blue));
        }
        return colors;
    }

    /**
     * Convertit une couleur hexadécimale en Color
     */
    public static Color hexToColor(String hex) {
        hex = hex.replace("#", "");
        return new Color(
                Integer.valueOf(hex.substring(0, 2), 16),
                Integer.valueOf(hex.substring(2, 4), 16),
                Integer.valueOf(hex.substring(4, 6), 16)
        );
    }
}