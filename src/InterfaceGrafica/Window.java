package InterfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class Window {

	private JFrame frame;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
	private JFormattedTextField frmtdtxtfldMemriaRecenteUsada;
	private JTable table_5;
	private JTable table_6;
	private JTable table_7;
	private JTable table_8;
	private JFormattedTextField frmtdtxtfldRegistradores;
	private JTable table_9;
	private JTable table_10;
	private JTable table_11;
	private JButton button;
	private JButton button_1;
	private JButton button_3;
	private JTable table_12;
	private JTextField txtClock;
	private JTextField txtClocks;
	private JTextField txtOpenFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 982, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setBounds(17, 52, 420, 176);
		table.setBorder(null);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setToolTipText("Estações de Reserva");
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", "", "", "", "", "", "", "", "", ""},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Tipo", "Busy", "Instru\u00E7\u00E3o", "Dets.", "Vj", "Vk", "Qj", "Qk", "A"
			}
		));
		table.getColumnModel().getColumn(0).setResizable(false);
		frame.getContentPane().add(table);
		
		JFormattedTextField frmtdtxtfldEstaesDeReserva = new JFormattedTextField();
		frmtdtxtfldEstaesDeReserva.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldEstaesDeReserva.setBackground(new Color(255, 255, 255));
		frmtdtxtfldEstaesDeReserva.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldEstaesDeReserva.setText("Esta\u00E7\u00F5es de Reserva");
		frmtdtxtfldEstaesDeReserva.setBounds(17, 11, 420, 20);
		frame.getContentPane().add(frmtdtxtfldEstaesDeReserva);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
    			new Object[][] {
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    				{null, null, null, null, null, null},
    			},
    			new String[] {
    				"Entrada", "Ocupado", "Instru\u00E7\u00E3o", "Estado", "Destino", "Valor"
    			}
    		));
		table_1.setBounds(17, 339, 420, 160);
		frame.getContentPane().add(table_1);
		
		JFormattedTextField frmtdtxtfldBufferDe = new JFormattedTextField();
		frmtdtxtfldBufferDe.setText("Buffer de Reordenação");
		frmtdtxtfldBufferDe.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldBufferDe.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldBufferDe.setBackground(Color.WHITE);
		frmtdtxtfldBufferDe.setBounds(17, 300, 420, 20);
		frame.getContentPane().add(frmtdtxtfldBufferDe);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{"Entrada", "Ocupado", "Instrução", "Estado", "Destino", "Valor"},
			},
			new String[] {
				"Entrada", "Ocupado", "Instrução", "Estado", "Destino", "Valor"
			}
		));
		table_2.setBounds(17, 321, 420, 20);
		frame.getContentPane().add(table_2);
		
		table_3 = new JTable();
		table_3.setModel(new DefaultTableModel(
			new Object[][] {
				{"ID", "Tipo", "Busy", "Instru\u00E7\u00E3o", "Dest.", "Vj", "Vk", "Qj", "Qk", "A"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table_3.setToolTipText("Esta\u00E7\u00F5es de Reserva");
		table_3.setSurrendersFocusOnKeystroke(true);
		table_3.setFillsViewportHeight(true);
		table_3.setColumnSelectionAllowed(true);
		table_3.setCellSelectionEnabled(true);
		table_3.setBorder(null);
		table_3.setBounds(17, 32, 420, 20);
		frame.getContentPane().add(table_3);
		
		table_4 = new JTable();
		table_4.setModel(new DefaultTableModel(
			new Object[][] {
				{"Endere\u00E7o", "Valor"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		table_4.setBounds(568, 179, 227, 15);
		frame.getContentPane().add(table_4);
		
		frmtdtxtfldMemriaRecenteUsada = new JFormattedTextField();
		frmtdtxtfldMemriaRecenteUsada.setText("Mem\u00F3ria Recente Usada");
		frmtdtxtfldMemriaRecenteUsada.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldMemriaRecenteUsada.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldMemriaRecenteUsada.setBackground(Color.WHITE);
		frmtdtxtfldMemriaRecenteUsada.setBounds(566, 160, 229, 20);
		frame.getContentPane().add(frmtdtxtfldMemriaRecenteUsada);
		
		table_5 = new JTable();
		table_5.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		));
		table_5.setBounds(568, 195, 227, 60);
		frame.getContentPane().add(table_5);
		
		table_6 = new JTable();
		table_6.setModel(new DefaultTableModel(
			new Object[][] {
				{"Clock Corrente:"},
				{"PC:"},
				{"N\u00FAmero de Instru\u00E7\u00F5es Conclu\u00EDdas:"},
				{"Clocks por Instru\u00E7\u00E3o:"},
			},
			new String[] {
				"New column"
			}
		));
		table_6.getColumnModel().getColumn(0).setPreferredWidth(170);
		table_6.setBounds(566, 277, 176, 64);
		frame.getContentPane().add(table_6);
		
		table_7 = new JTable();
		table_7.setBounds(561, 224, -37, -50);
		frame.getContentPane().add(table_7);
		
		table_8 = new JTable();
		table_8.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"New column"
			}
		));
		table_8.setBounds(744, 277, 51, 63);
		frame.getContentPane().add(table_8);
		
		frmtdtxtfldRegistradores = new JFormattedTextField();
		frmtdtxtfldRegistradores.setText("Registradores");
		frmtdtxtfldRegistradores.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldRegistradores.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldRegistradores.setBackground(Color.WHITE);
		frmtdtxtfldRegistradores.setBounds(518, 353, 418, 20);
		frame.getContentPane().add(frmtdtxtfldRegistradores);
		
		table_9 = new JTable();
		table_9.setModel(new DefaultTableModel(
			new Object[][] {
				{"Registrador", "Qi", "Vi"},
				{"R0", null, null},
				{"R1", null, null},
				{"R2", null, null},
				{"R3", null, null},
				{"R4", null, null},
				{"R5", null, null},
				{"R6", null, null},
				{"R7", null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_9.setBounds(520, 372, 101, 144);
		frame.getContentPane().add(table_9);
		
		table_10 = new JTable();
		table_10.setModel(new DefaultTableModel(
			new Object[][] {
				{"Registradores", "Qi", "Vi"},
				{"R8", null, null},
				{"R9", null, null},
				{"R10", null, null},
				{"R11", null, null},
				{"R12", null, null},
				{"R13", null, null},
				{"R14", null, null},
				{"R15", null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_10.setBounds(624, 371, 101, 144);
		frame.getContentPane().add(table_10);
		
		table_11 = new JTable();
		table_11.setModel(new DefaultTableModel(
			new Object[][] {
				{"Registradores", "Qi", "Vi"},
				{"R16", null, null},
				{"R17", null, null},
				{"R18", null, null},
				{"R19", null, null},
				{"R20", null, null},
				{"R21", null, null},
				{"R22", null, null},
				{"R23", null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_11.setBounds(730, 372, 101, 144);
		frame.getContentPane().add(table_11);
		
		table_12 = new JTable();
		table_12.setModel(new DefaultTableModel(
			new Object[][] {
				{"Registradores", "Qi", "Vi"},
				{"R24", null, null},
				{"R25", null, null},
				{"R26", null, null},
				{"R27", null, null},
				{"R28", null, null},
				{"R29", null, null},
				{"R30", null, null},
				{"R31", null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_12.setBounds(835, 372, 101, 144);
		frame.getContentPane().add(table_12);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ran = MIPS.run1Clock();
				UpdateTableModels();
				if(ran == -1){
					JOptionPane.showMessageDialog(frame, "Execucao Finalizada");
				}
			}
		});
		button.setForeground(new Color(0, 0, 0));
		button.setBackground(UIManager.getColor("Button.background"));
		button.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/icon-play-128.png")));
		button.setBounds(506, 11, 106, 104);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ran = MIPS.run10Clocks();
				UpdateTableModels();
				if(ran == -1){
					JOptionPane.showMessageDialog(frame, "Execucao Finalizada");
				}
			}
		});
		button_1.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/60979.png")));
		button_1.setBounds(641, 11, 132, 103);
		frame.getContentPane().add(button_1);
		
		button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFile = new JFileChooser();
				int returnVal = openFile.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            MIPS.file = openFile.getSelectedFile();
		            MIPS.initialize();
		            table.setModel(new DefaultTableModel(
		        			new Object[][] {
		        				{MIPS.cargaFP[0].getID(), MIPS.cargaFP[0].getTipo(), MIPS.cargaFP[0].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.cargaFP[1].getID(), MIPS.cargaFP[1].getTipo(), MIPS.cargaFP[1].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.cargaFP[2].getID(), MIPS.cargaFP[2].getTipo(), MIPS.cargaFP[2].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.cargaFP[3].getID(), MIPS.cargaFP[3].getTipo(), MIPS.cargaFP[3].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.cargaFP[4].getID(), MIPS.cargaFP[4].getTipo(), MIPS.cargaFP[4].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.somaFP[0].getID(), MIPS.somaFP[0].getTipo(), MIPS.somaFP[0].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.somaFP[1].getID(), MIPS.somaFP[1].getTipo(), MIPS.somaFP[1].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.somaFP[2].getID(), MIPS.somaFP[2].getTipo(), MIPS.somaFP[2].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.multFP[0].getID(), MIPS.multFP[0].getTipo(), MIPS.multFP[0].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.multFP[1].getID(), MIPS.multFP[1].getTipo(), MIPS.multFP[1].getBusy(), null, null, null, null, null, null, null},
		        				{MIPS.multFP[2].getID(), MIPS.multFP[2].getTipo(), MIPS.multFP[2].getBusy(), "", "", "", "", "", "", ""},
		        			},
		        			new String[] {
		        				"ID", "Tipo", "Busy", "Instru\u00E7\u00E3o", "Dets.", "Vj", "Vk", "Qj", "Qk", "A"
		        			}
		        		));
		            table_1.setModel(new DefaultTableModel(
		        			new Object[][] {
		        				{"B0", "No", null, null, null, null},
		        				{"B1", "No", null, null, null, null},
		        				{"B2", "No", null, null, null, null},
		        				{"B3", "No", null, null, null, null},
		        				{"B4", "No", null, null, null, null},
		        				{"B5", "No", null, null, null, null},
		        				{"B6", "No", null, null, null, null},
		        				{"B7", "No", null, null, null, null},
		        				{"B8", "No", null, null, null, null},
		        				{"B9", "No", null, null, null, null},
		        			},
		        			new String[] {
		        				"Entrada", "Ocupado", "Instru\u00E7\u00E3o", "Estado", "Destino", "Valor"
		        			}
		        		));
		            UpdateTableModels();
		            //This is where a real application would open the file.
		            //log.append("Opening: " + file.getName() + "." + newline);
		        } else {
		        	JOptionPane.showMessageDialog(frame, "Arquivo Invalido");
		            //log.append("Open command cancelled by user." + newline);
		        }
			}
		});
		button_3.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/kde-folder-drag-accept.png")));
		button_3.setBounds(799, 12, 137, 99);
		frame.getContentPane().add(button_3);
		
		txtClock = new JTextField();
		txtClock.setText("1 Clock");
		txtClock.setBounds(538, 126, 44, 20);
		frame.getContentPane().add(txtClock);
		txtClock.setColumns(10);
		
		txtClocks = new JTextField();
		txtClocks.setText("10 Clocks");
		txtClocks.setBounds(663, 125, 65, 20);
		frame.getContentPane().add(txtClocks);
		txtClocks.setColumns(10);
		
		txtOpenFile = new JTextField();
		txtOpenFile.setText("Open File");
		txtOpenFile.setBounds(835, 122, 64, 20);
		frame.getContentPane().add(txtOpenFile);
		txtOpenFile.setColumns(10);
		
		
	}
	
	public void UpdateTableModels(){
		UpdateTable1();
		UpdateTable2();
		table_8.setModel(new DefaultTableModel(
				new Object[][] {
					{MIPS.clock},
					{MIPS.PC},
					{null},
					{null},
				},
				new String[] {
					"New column"
				}
			));
		table_9.setModel(new DefaultTableModel(
				new Object[][] {
					{"Registrador", "Qi", "Vi"},
					{"R0", MIPS.registradores[0].getQi(), MIPS.registradores[0].getVi()},
					{"R1", MIPS.registradores[1].getQi(), MIPS.registradores[1].getVi()},
					{"R2", MIPS.registradores[2].getQi(), MIPS.registradores[2].getVi()},
					{"R3", MIPS.registradores[3].getQi(), MIPS.registradores[3].getVi()},
					{"R4", MIPS.registradores[4].getQi(), MIPS.registradores[4].getVi()},
					{"R5", MIPS.registradores[5].getQi(), MIPS.registradores[5].getVi()},
					{"R6", MIPS.registradores[6].getQi(), MIPS.registradores[6].getVi()},
					{"R7", MIPS.registradores[7].getQi(), MIPS.registradores[7].getVi()},
				},
				new String[] {
					"New column", "New column", "New column"
				}
			));
		table_10.setModel(new DefaultTableModel(
				new Object[][] {
					{"Registradores", "Qi", "Vi"},
					{"R8", MIPS.registradores[8].getQi(), MIPS.registradores[8].getVi()},
					{"R9", MIPS.registradores[9].getQi(), MIPS.registradores[9].getVi()},
					{"R10", MIPS.registradores[10].getQi(), MIPS.registradores[10].getVi()},
					{"R11", MIPS.registradores[11].getQi(), MIPS.registradores[11].getVi()},
					{"R12", MIPS.registradores[12].getQi(), MIPS.registradores[12].getVi()},
					{"R13", MIPS.registradores[13].getQi(), MIPS.registradores[13].getVi()},
					{"R14", MIPS.registradores[14].getQi(), MIPS.registradores[14].getVi()},
					{"R15", MIPS.registradores[15].getQi(), MIPS.registradores[15].getVi()},
				},
				new String[] {
					"New column", "New column", "New column"
				}
			));
		table_11.setModel(new DefaultTableModel(
				new Object[][] {
					{"Registradores", "Qi", "Vi"},
					{"R16", MIPS.registradores[16].getQi(), MIPS.registradores[16].getVi()},
					{"R17", MIPS.registradores[17].getQi(), MIPS.registradores[17].getVi()},
					{"R18", MIPS.registradores[18].getQi(), MIPS.registradores[18].getVi()},
					{"R19", MIPS.registradores[19].getQi(), MIPS.registradores[19].getVi()},
					{"R20", MIPS.registradores[20].getQi(), MIPS.registradores[20].getVi()},
					{"R21", MIPS.registradores[21].getQi(), MIPS.registradores[21].getVi()},
					{"R22", MIPS.registradores[22].getQi(), MIPS.registradores[22].getVi()},
					{"R23", MIPS.registradores[23].getQi(), MIPS.registradores[23].getVi()},
				},
				new String[] {
					"New column", "New column", "New column"
				}
			));
		table_12.setModel(new DefaultTableModel(
				new Object[][] {
					{"Registradores", "Qi", "Vi"},
					{"R24", MIPS.registradores[24].getQi(), MIPS.registradores[24].getVi()},
					{"R25", MIPS.registradores[25].getQi(), MIPS.registradores[25].getVi()},
					{"R26", MIPS.registradores[26].getQi(), MIPS.registradores[26].getVi()},
					{"R27", MIPS.registradores[27].getQi(), MIPS.registradores[27].getVi()},
					{"R28", MIPS.registradores[28].getQi(), MIPS.registradores[28].getVi()},
					{"R29", MIPS.registradores[29].getQi(), MIPS.registradores[29].getVi()},
					{"R30", MIPS.registradores[30].getQi(), MIPS.registradores[30].getVi()},
					{"R31", MIPS.registradores[31].getQi(), MIPS.registradores[31].getVi()},
				},
				new String[] {
					"New column", "New column", "New column"
				}
			));
	}

	public void UpdateTable1() {
		// TODO Auto-generated method stub
		for(int i = 0; i < MIPS.cargaFP.length; i++){
			if(MIPS.cargaFP[i].isBusy()){
				table.setValueAt(MIPS.cargaFP[i].getBusy(), i, 2);
				table.setValueAt(MIPS.cargaFP[i].getInst(), i, 3);
				table.setValueAt(MIPS.cargaFP[i].getDest(), i, 4);
				table.setValueAt(MIPS.cargaFP[i].getVj(), i, 5);
				table.setValueAt(MIPS.cargaFP[i].getVk(), i, 6);
				table.setValueAt(MIPS.cargaFP[i].getQj(), i, 7);
				table.setValueAt(MIPS.cargaFP[i].getQk(), i, 8);
				table.setValueAt(MIPS.cargaFP[i].getA(), i, 9);
			}
			else{
				table.setValueAt(MIPS.cargaFP[i].getBusy(), i, 2);
				table.setValueAt(null, i, 3);
				table.setValueAt(null, i, 4);
				table.setValueAt(null, i, 5);
				table.setValueAt(null, i, 6);
				table.setValueAt(null, i, 7);
				table.setValueAt(null, i, 8);
				table.setValueAt(null, i, 9);
			}
		}
		for(int i = 0; i < 3; i++){
			if(MIPS.somaFP[i].isBusy()){
				table.setValueAt(MIPS.somaFP[i].getBusy(), i + 5, 2);
				table.setValueAt(MIPS.somaFP[i].getInst(), i + 5, 3);
				table.setValueAt(MIPS.somaFP[i].getDest(), i + 5, 4);
				table.setValueAt(MIPS.somaFP[i].getVj(), i + 5, 5);
				table.setValueAt(MIPS.somaFP[i].getVk(), i + 5, 6);
				table.setValueAt(MIPS.somaFP[i].getQj(), i + 5, 7);
				table.setValueAt(MIPS.somaFP[i].getQk(), i + 5, 8);
				table.setValueAt(MIPS.somaFP[i].getA(), i + 5, 9);
			}
			else{
				table.setValueAt(MIPS.somaFP[i].getBusy(), i + 5, 2);
				table.setValueAt(null, i + 5, 3);
				table.setValueAt(null, i + 5, 4);
				table.setValueAt(null, i + 5, 5);
				table.setValueAt(null, i + 5, 6);
				table.setValueAt(null, i + 5, 7);
				table.setValueAt(null, i + 5, 8);
				table.setValueAt(null, i + 5, 9);
			}
		}
		for(int i = 0; i < 3; i++){
			if(MIPS.multFP[i].isBusy()){
				table.setValueAt(MIPS.multFP[i].getBusy(), i + 8, 2);
				table.setValueAt(MIPS.multFP[i].getInst(), i + 8, 3);
				table.setValueAt(MIPS.multFP[i].getDest(), i + 8, 4);
				table.setValueAt(MIPS.multFP[i].getVj(), i + 8, 5);
				table.setValueAt(MIPS.multFP[i].getVk(), i + 8, 6);
				table.setValueAt(MIPS.multFP[i].getQj(), i + 8, 7);
				table.setValueAt(MIPS.multFP[i].getQk(), i + 8, 8);
				table.setValueAt(MIPS.multFP[i].getA(), i + 8, 9);
			}
			else{
				table.setValueAt(MIPS.multFP[i].getBusy(), i + 8, 2);
				table.setValueAt(null, i + 8, 3);
				table.setValueAt(null, i + 8, 4);
				table.setValueAt(null, i + 8, 5);
				table.setValueAt(null, i + 8, 6);
				table.setValueAt(null, i + 8, 7);
				table.setValueAt(null, i + 8, 8);
				table.setValueAt(null, i + 8, 9);
			}
		}
	}
	
	public void UpdateTable2(){
		for(int i = 0; i < 10; i++){
			if(MIPS.buffer.getItemBuffer(i).isBusy()){
				table_1.setValueAt(MIPS.buffer.getItemBuffer(i).getBusy(), i, 1);
				table_1.setValueAt(MIPS.buffer.getItemBuffer(i).getInstrucao(), i, 2);
				table_1.setValueAt(MIPS.buffer.getItemBuffer(i).getEstado(), i, 3);
				table_1.setValueAt(MIPS.buffer.getItemBuffer(i).getDestino(), i, 4);
				table_1.setValueAt(MIPS.buffer.getItemBuffer(i).getValor(), i, 5);
			}
			else{
				table_1.setValueAt(MIPS.buffer.getItemBuffer(i).getBusy(), i, 1);
				table_1.setValueAt(null, i, 2);
				table_1.setValueAt(null, i, 3);
				table_1.setValueAt(null, i, 4);
				table_1.setValueAt(null, i, 5);
			}
		}
	}
}
