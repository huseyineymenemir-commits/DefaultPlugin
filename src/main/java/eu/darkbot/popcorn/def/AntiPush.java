package com.custom.actionbuttons;

import com.github.manolo8.darkbot.Main;
import com.github.manolo8.darkbot.core.itf.InstructionProvider;
import com.github.manolo8.darkbot.core.itf.Task;
import com.github.manolo8.darkbot.extensions.features.Feature;
import com.github.manolo8.darkbot.gui.plugins.PluginOptionsPane;

import javax.swing.*;
import java.awt.*;

@Feature(name = "Eylem Düğmeleri", description = "Manuel tetiklenebilir eylem butonları ekler.")
public class ActionButtonPlugin implements Task, InstructionProvider, PluginOptionsPane {

    private Main main;
    private JPanel guiPanel;

    private boolean forceSellPalladium = false;
    private boolean forceFinishGGAndReturn = false;
    private boolean forceSellResources = false;

    @Override
    public void install(Main main) {
        this.main = main;
        setupGUI();
    }

    private void setupGUI() {
        guiPanel = new JPanel();
        guiPanel.setLayout(new GridLayout(3, 1, 5, 5));

        JButton btnPalladium = new JButton("🔄 Palladium Satışına Git (15'in Katı)");
        JButton btnGG = new JButton("🌌 GG Haritasını Bitir & X-1'e Dön");
        JButton btnSellOres = new JButton("💎 Cevherleri Manuel Sat");

        btnPalladium.addActionListener(e -> {
            forceSellPalladium = true;
            System.out.println("[Eylem Düğmeleri] Palladium satışı tetiklendi!");
        });

        btnGG.addActionListener(e -> {
            forceFinishGGAndReturn = true;
            System.out.println("[Eylem Düğmeleri] GG bitirme ve X-1'e dönme tetiklendi!");
        });

        btnSellOres.addActionListener(e -> {
            forceSellResources = true;
            System.out.println("[Eylem Düğmeleri] Cevher satışı tetiklendi!");
        });

        guiPanel.add(btnPalladium);
        guiPanel.add(btnGG);
        guiPanel.add(btnSellOres);
    }

    @Override
    public void tick() {
        if (!main.hero.isValid()) return;

        if (forceSellPalladium) {
            int currentPal = main.statsManager.statues.palladium; 
            int targetPal = (currentPal / 15) * 15;
            System.out.println("[Eylem] " + targetPal + " Palladium satışı başlatılıyor...");
            forceSellPalladium = false;
        }

        if (forceFinishGGAndReturn) {
            System.out.println("[Eylem] GG haritası bitince X-1'e dönülecek.");
            forceFinishGGAndReturn = false;
        }

        if (forceSellResources) {
            System.out.println("[Eylem] Cevher satışı başlatılıyor.");
            forceSellResources = false;
        }
    }

    @Override
    public JComponent getOptionsPane() {
        return guiPanel;
    }

    @Override
    public String instruction() {
        return "Bota tek tıkla manuel komutlar göndermenizi sağlayan eylem butonları.";
    }
}
