package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import jaco.mp3.player.MP3Player;

/**
 * Classe respons�vel por realizar a montagem e atualiza��o do componente visual, bem como
 * a execu��o musical do projeto
 * @author Kalleo Leandro dos Santos Leal
 * @since 14/02/2022
 * @version 1.0
 */

public class Clipe {
	ImageIcon imagem = new ImageIcon(getClass().getResource("/Bad Apple 0001.jpg"));
    private JLabel label;
    private boolean running;
    String basePath = System.getProperty("user.dir");            	    

    public Clipe() {
        label = new JLabel(imagem);
        running = true;

        JFrame janela = new JFrame();
        JPanel painelPrincipal = (JPanel) janela.getContentPane();

        label.setBounds(0, 0, 960, 720);
        painelPrincipal.add(label);

        janela.setSize(960, 720);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        File f1 = new File("src\\music\\bad apple.mp3");
        System.out.println(f1.getAbsolutePath());
        MP3Player m = new MP3Player(f1);
        

        Timer timer = new Timer(33, new ActionListener() {
            int i = 0;
            int segundos = 0;
            LocalDateTime d1 = LocalDateTime.now();

            @Override
            public void actionPerformed(ActionEvent e) {
            	LocalDateTime d2 = LocalDateTime.now();
                if (running) {                    
                	long tempo = (d1.until(d2, ChronoUnit.SECONDS));

                    if (tempo < 1) {
                        int concat = (segundos * 30) + i;
                        String b = String.format("/Bad Apple %04d.jpg", concat + 1);                        
                        if (i <= 30) {
                            ImageIcon image = new ImageIcon(getClass().getResource(b));
                            label.setIcon(image);
                            SwingUtilities.updateComponentTreeUI(janela);
                            i++;
                        }
                    } else {
                        segundos++;                        
                        i = 0;
                        d1 = LocalDateTime.now();
                    }

                    if (segundos * 30 + i >= 6572) {
                        running = false;
                    }
                } else {
                    ((Timer) e.getSource()).stop();
                    m.stop();

                    LocalDateTime dFinal = LocalDateTime.now();
                    System.out.println(dFinal);
                    JOptionPane.showMessageDialog(null, "FIM", "Touhou Project", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        timer.start();
        m.play();
    }
}