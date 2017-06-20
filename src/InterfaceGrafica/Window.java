package InterfaceGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
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
	private JButton button_2;
	private JButton button_3;

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
		table.setBounds(0, 41, 420, 98);
		table.setBorder(null);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setToolTipText("Esta\u00E7\u00F5es de Reserva");
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", "", "", "", "", "", "", "", "", ""},
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
		frmtdtxtfldEstaesDeReserva.setBounds(0, 0, 420, 20);
		frame.getContentPane().add(frmtdtxtfldEstaesDeReserva);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null, null, null, null},
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
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"Entrada", "Ocupado", "instru\u00E7\u00E3o", "Estado", "Destino", "Valor"
			}
		));
		table_1.setBounds(0, 193, 420, 223);
		frame.getContentPane().add(table_1);
		
		JFormattedTextField frmtdtxtfldBufferDe = new JFormattedTextField();
		frmtdtxtfldBufferDe.setText("Buffer de Reordena\u00E7\u00E3o");
		frmtdtxtfldBufferDe.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldBufferDe.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldBufferDe.setBackground(Color.WHITE);
		frmtdtxtfldBufferDe.setBounds(0, 154, 420, 20);
		frame.getContentPane().add(frmtdtxtfldBufferDe);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(420, 175, 17, 241);
		frame.getContentPane().add(scrollBar);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(420, 23, 17, 112);
		frame.getContentPane().add(scrollBar_1);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{"Entrada", "Ocupado", "Instru\u00E7\u00E3o", "Estado", "Destino", "Valor"},
			},
			new String[] {
				"Entrada", "Ocupado", "Instru\u00E7\u00E3o", "Estado", "Destino", "Valor"
			}
		));
		table_2.setBounds(0, 175, 420, 20);
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
		table_3.setBounds(0, 21, 420, 20);
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
		table_4.setBounds(566, 154, 227, 15);
		frame.getContentPane().add(table_4);
		
		frmtdtxtfldMemriaRecenteUsada = new JFormattedTextField();
		frmtdtxtfldMemriaRecenteUsada.setText("Mem\u00F3ria Recente Usada");
		frmtdtxtfldMemriaRecenteUsada.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldMemriaRecenteUsada.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldMemriaRecenteUsada.setBackground(Color.WHITE);
		frmtdtxtfldMemriaRecenteUsada.setBounds(564, 135, 229, 20);
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
		table_5.setBounds(566, 170, 227, 60);
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
		table_6.setBounds(569, 258, 176, 64);
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
		table_8.setBounds(742, 258, 51, 63);
		frame.getContentPane().add(table_8);
		
		frmtdtxtfldRegistradores = new JFormattedTextField();
		frmtdtxtfldRegistradores.setText("Registradores");
		frmtdtxtfldRegistradores.setHorizontalAlignment(SwingConstants.CENTER);
		frmtdtxtfldRegistradores.setFont(new Font("Arial", Font.PLAIN, 12));
		frmtdtxtfldRegistradores.setBackground(Color.WHITE);
		frmtdtxtfldRegistradores.setBounds(518, 353, 313, 20);
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
		
		button = new JButton("");
		button.setForeground(new Color(0, 0, 0));
		button.setBackground(UIManager.getColor("Button.background"));
		button.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/icon-play-128.png")));
		button.setBounds(447, 10, 106, 104);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/60979.png")));
		button_1.setBounds(559, 11, 132, 103);
		frame.getContentPane().add(button_1);
		
		button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/icon-pause-128.png")));
		button_2.setBounds(697, 11, 96, 103);
		frame.getContentPane().add(button_2);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(Window.class.getResource("/InterfaceGrafica/Icones/kde-folder-drag-accept.png")));
		button_3.setBounds(799, 12, 137, 99);
		frame.getContentPane().add(button_3);
	}
}
