package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jaco.mp3.player.MP3Player;

/**
 * Classe respons�vel por realizar a montagem e atualiza��o do componente visual, bem como
 * a execu��o musical do projeto
 * @author Kalleo Leandro dos Santos Leal
 * @since 14/02/2022
 * @version 1.0
 */

public class Clipe 
{	
	ImageIcon imagem = new ImageIcon(getClass().getResource("..\\img\\Bad Apple 0001.jpg"));
	
	JLabel label = new JLabel(imagem);
	
	public Clipe() throws InterruptedException, FileNotFoundException
	{
		JFrame janela = new JFrame();		
		
		//Declara��do do JPanel para organizar os elementos na tela	
		JPanel painelPrincipal;
		
						
		painelPrincipal = (JPanel) janela.getContentPane();

		//Setando o tamanho do label que conter� as imagens com o mesmo tamanho das mesmas
		label.setBounds(0,0,960,720);	
		
		//Adicionando o label ao painel				
		
		painelPrincipal.add(label);
		
		//Tamanho da tela
		janela.setSize(960,720);		        
				
		//Adicionando menus a tela		
				
		//Configurando a a��o do bot�o fechar
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Configurando a tela para n�o modificar o tamanho
		janela.setResizable(false);
				
		//configurando a tela para posicionamento central
		janela.setLocationRelativeTo(null);		
				
		//Configurando a visibilidade da janela
		janela.setVisible(true);
		
		/*Mudar o absolute patch caso queira usar uma vers�o da m�sica pr�pria, lembrando que Bad Apple
		* possui 3:39 minutos de dura��o
		* */
		File f1 =  new File("src\\music\\bad apple.mp3");
		System.out.println(f1.getAbsolutePath());
		MP3Player m = new  MP3Player(f1);
		m.play();
		
		//Controles de tempo e estado
		
		boolean teste = true;		
		LocalDateTime d1 = LocalDateTime.now();
		LocalDateTime dInicial = d1;		
		LocalDateTime d2;
		
		int i=0;
		long tempo = 0;
		int segundos = 0;
		int concat=0;
		
		//Controle de frames	
		/*
		 *Aqui � onde � feita a leitura dos arquivos de imagem, onde os 6572 frames s�o lidos e reproduzidos
		 *sendo controlados a 30fps a amostragem para evitar a acelera��o do v�deo
		 * **/
		do
		{
			d2 = LocalDateTime.now();
			tempo = (d1.until(d2, ChronoUnit.SECONDS));			
			if(tempo < 1)
			{				
				String b = "";				
				concat = (segundos*30)+i;									
				if(concat == 0)
				{
					b = "..\\img\\Bad Apple 0001.jpg";
				}
				else if(concat >=1 && concat <10)				{
					
					
					b = "..\\img\\Bad Apple 000" + concat + ".jpg";					
				}
				else if(concat>=10 && concat<100)				
				{
					b = "..\\img\\Bad Apple 00" + concat+ ".jpg";
					
				}				
				else if(concat>=100 && concat<1000)
				{
					b = "..\\img\\Bad Apple 0" + concat + ".jpg";					
				}
				else
				{
					b = "..\\img\\Bad Apple " + concat + ".jpg";					
				}				
				
				if(i<=30)
				{
					/*				 
					 * Aqui tamb�m � feito a atualiza��o do JLabel com a imagem nova e o incremento do contador que l� as imagens
					 * (pode ser substituido por uma lista a ser lida sequencialmente, achei muito empenho, deixei assim)   
					 * */
					ImageIcon image = new ImageIcon(getClass().getResource(b));				
					label.setIcon(image);							
					SwingUtilities.updateComponentTreeUI(janela);
					i++;			
					
					/*
					 * Mesmo dispondo de um controle de segundos, a Thread em quest�o ainda tem vari�ncia,
					 * para evitar isso realizei essa sequ�ncia de sleeps em determinados conjuntos de frames.
					 */
					
					if(segundos < 111) 
					{
						Thread.sleep((long)25);					
					}
					else if(segundos >= 111 && segundos < 131)
					{
						Thread.sleep((long)24);
					}
					else if(segundos >= 131 && segundos < 170)
					{
						Thread.sleep((long)22.5);
					}
					else if(segundos >= 181 && segundos < 200)
					{
						Thread.sleep((long)24.250);
					}
					else
					{
						Thread.sleep((long)25.250);
					}					
				}				
			}
			else
			{			
				segundos++;
				System.out.println(segundos);
				d1 = LocalDateTime.now();				
				i=0;
			}
			//Aqui quebramos a atualiza��o da tela
			if(segundos*30+i >=6572)
			{
				teste=false;
			}			
		}while(teste);
		
		/*As 3 linhas abaixo podem ser desconsideradas do projeto, apenas servem para exibi��o de tempo total em segundos para
		 * compara��o de atraso(o v�deo possui 219 segundos, pode atrasar ou adiantar no m�ximo 2 sem que o espectador perceba)
		 */
		LocalDateTime dFinal = LocalDateTime.now();
		System.out.println(dFinal);		
		System.out.println(dInicial.until(dFinal,ChronoUnit.SECONDS));
		
		//Uma pequena exibi��o visual de fim de v�deo		
		JOptionPane.showMessageDialog(null, "FIM", "Touhou Project", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	
	
}
