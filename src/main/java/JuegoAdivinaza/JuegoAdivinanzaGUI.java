package JuegoAdivinaza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class JuegoAdivinanzaGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Componentes para el menú principal
    private JPanel menuPanel;
    private JButton btnIniciarJuego;
    private JButton btnAcercaDe;
    private JButton btnSalir;
    private JButton btnMultijugador;

    // Componentes para panel de juego (un jugador y multijugador)
    private JPanel gamePanel; 
    private JComboBox<String> cmbCategorias;
    private JLabel lblPista;
    private JLabel lblProgresoPalabra;
    private JLabel lblIntentosRestantes;
    private JTextField txtEntrada;
    private JButton btnEnviar;
    private JButton btnNuevoJuego;
    private JButton btnVolverMenu;

    // Barra superior con información del juego (jugador actual, puntuaciones, tiempo)
    private JPanel infoBarPanel;
    private JLabel lblCurrentPlayer;
    private JLabel lblPlayer1Score;
    private JLabel lblPlayer2Score;
    private JLabel lblTimer;

    // Puntuación actual del jugador (trasladada desde la barra de información para un solo jugador)
    private JLabel lblCurrentGamePoints; 

    // Componentes para la configuración multijugador
    private JPanel multiplayerSetupPanel;
    private JTextField txtPlayer1Name;
    private JTextField txtPlayer2Name;
    private JButton btnStartMultiplayer;

    // Instancia de lógica del juego
    private JuegoAdivinanza juego;
    private GestorExcel gestorExcel; 

    // Variables específicas del modo multijugador
    private List<Jugador> jugadores;
    private int currentPlayerIndex;
    private boolean isMultiplayerMode = false;
    private int currentRound = 1; 
    private Timer guiTimer; 
    private int timeLeft; 

    public JuegoAdivinanzaGUI() {
        setTitle("JUEGO DE ADIVINANZAS - PROJECT MANHATTAN");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        gestorExcel = new GestorExcel(); 
        juego = new JuegoAdivinanza(gestorExcel); 

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createMainMenuPanel();
        createGamePanel();
        createMultiplayerSetupPanel();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(multiplayerSetupPanel, "MultiplayerSetup");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
    }

    private void createMainMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(new Color(45, 45, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("JUEGO DE ADIVINANZAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        menuPanel.add(titleLabel, gbc);

        JLabel projectLabel = new JLabel("PROJECT MANHATTAN");
        projectLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        projectLabel.setForeground(new Color(180, 180, 180));
        gbc.gridy = 1;
        menuPanel.add(projectLabel, gbc);

        btnIniciarJuego = createStyledButton("Iniciar juego (1 Jugador)");
        btnIniciarJuego.addActionListener(e -> showGamePanel(false));
        gbc.gridy = 2;
        menuPanel.add(btnIniciarJuego, gbc);

        btnMultijugador = createStyledButton("Modo multijugador (Versus)");
        btnMultijugador.addActionListener(e -> cardLayout.show(mainPanel, "MultiplayerSetup"));
        gbc.gridy = 3;
        menuPanel.add(btnMultijugador, gbc);

        btnAcercaDe = createStyledButton("Acerca de");
        btnAcercaDe.addActionListener(e -> showAcercaDe());
        gbc.gridy = 4;
        menuPanel.add(btnAcercaDe, gbc);

        btnSalir = createStyledButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        gbc.gridy = 5;
        menuPanel.add(btnSalir, gbc);
    }

    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(10, 10)); // Diseño exterior
        gamePanel.setBackground(new Color(30, 30, 30));

        // --- Barra de información superior (reproductor actual, puntuaciones, tiempo) ---
        infoBarPanel = new JPanel(new GridBagLayout());
        infoBarPanel.setBackground(new Color(60, 60, 60));
        infoBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(0, 15, 0, 15);
        gbcInfo.fill = GridBagConstraints.HORIZONTAL;
        gbcInfo.anchor = GridBagConstraints.WEST;

        lblCurrentPlayer = createStyledLabel("Jugador Actual: -");
        lblCurrentPlayer.setFont(new Font("Arial", Font.BOLD, 14));
        gbcInfo.gridx = 0;
        gbcInfo.gridy = 0;
        infoBarPanel.add(lblCurrentPlayer, gbcInfo);

        lblPlayer1Score = createStyledLabel("P1: 0");
        lblPlayer1Score.setFont(new Font("Arial", Font.BOLD, 14));
        gbcInfo.gridx = 1;
        gbcInfo.gridy = 0;
        infoBarPanel.add(lblPlayer1Score, gbcInfo);

        lblPlayer2Score = createStyledLabel("P2: 0");
        lblPlayer2Score.setFont(new Font("Arial", Font.BOLD, 14));
        gbcInfo.gridx = 2;
        gbcInfo.gridy = 0;
        infoBarPanel.add(lblPlayer2Score, gbcInfo);

        lblTimer = createStyledLabel("Tiempo: 60s");
        lblTimer.setFont(new Font("Arial", Font.BOLD, 14));
        gbcInfo.gridx = 3;
        gbcInfo.gridy = 0;
        gbcInfo.anchor = GridBagConstraints.EAST;
        gbcInfo.weightx = 1.0; 
        infoBarPanel.add(lblTimer, gbcInfo);

        gamePanel.add(infoBarPanel, BorderLayout.NORTH);

        // --- Contenido principal del juego (pista, progreso, entrada, botones, puntos) ---
        JPanel centralGameContent = new JPanel();
        centralGameContent.setLayout(new BoxLayout(centralGameContent, BoxLayout.Y_AXIS));
        centralGameContent.setBackground(new Color(30, 30, 30));
        centralGameContent.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Selección de categoría, botón de nuevo juego y botón de volver al menú.
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlsPanel.setBackground(new Color(45, 45, 45));
        controlsPanel.add(createStyledLabel("Categoría:"));
        cmbCategorias = new JComboBox<>();
        cmbCategorias.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbCategorias.setPreferredSize(new Dimension(150, 30));
        controlsPanel.add(cmbCategorias);

        btnNuevoJuego = createStyledButton("Nuevo Juego");
        btnNuevoJuego.addActionListener(e -> iniciarNuevoJuego());
        controlsPanel.add(btnNuevoJuego);

        btnVolverMenu = createStyledButton("Volver al Menú");
        btnVolverMenu.addActionListener(e -> returnToMainMenu());
        controlsPanel.add(btnVolverMenu);

        centralGameContent.add(controlsPanel);
        centralGameContent.add(Box.createVerticalStrut(20));

        // Pista
        lblPista = createStyledLabel("PISTA: ");
        lblPista.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPista.setFont(new Font("Arial", Font.BOLD, 18));
        centralGameContent.add(lblPista);
        centralGameContent.add(Box.createVerticalStrut(20));

        // Progreso Palabra
        lblProgresoPalabra = createStyledLabel("Progreso: _______");
        lblProgresoPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblProgresoPalabra.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblProgresoPalabra.setForeground(new Color(255, 255, 0));
        centralGameContent.add(lblProgresoPalabra);
        centralGameContent.add(Box.createVerticalStrut(10));

        // Intentos Restantes
        lblIntentosRestantes = createStyledLabel("Intentos restantes: 6");
        lblIntentosRestantes.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIntentosRestantes.setFont(new Font("Arial", Font.PLAIN, 16));
        centralGameContent.add(lblIntentosRestantes);
        centralGameContent.add(Box.createVerticalStrut(20));

        // Sección de entrada y botón «Enviar»
        JPanel inputAndButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        inputAndButtonPanel.setBackground(new Color(30, 30, 30));
        inputAndButtonPanel.add(createStyledLabel("Escribe una letra o la palabra completa:"));
        txtEntrada = new JTextField(20);
        txtEntrada.setFont(new Font("Arial", Font.PLAIN, 16));
        txtEntrada.setPreferredSize(new Dimension(250, 30));
        inputAndButtonPanel.add(txtEntrada);

        btnEnviar = createStyledButton("Enviar");
        btnEnviar.addActionListener(e -> processGuess());
        txtEntrada.addActionListener(e -> processGuess()); 
        inputAndButtonPanel.add(btnEnviar);
        centralGameContent.add(inputAndButtonPanel);
        centralGameContent.add(Box.createVerticalStrut(10));

        // Puntos actuales del juego (debajo del botón Enviar)
        lblCurrentGamePoints = createStyledLabel("Puntos de esta ronda: 0");
        lblCurrentGamePoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCurrentGamePoints.setFont(new Font("Arial", Font.BOLD, 16));
        lblCurrentGamePoints.setForeground(new Color(120, 255, 120));
        centralGameContent.add(lblCurrentGamePoints);
        centralGameContent.add(Box.createVerticalStrut(20));

        gamePanel.add(centralGameContent, BorderLayout.CENTER);
    }

    private void createMultiplayerSetupPanel() {
        multiplayerSetupPanel = new JPanel(new GridBagLayout());
        multiplayerSetupPanel.setBackground(new Color(45, 45, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Configuración Multijugador");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        multiplayerSetupPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        multiplayerSetupPanel.add(createStyledLabel("Nombre del Jugador 1:"), gbc);
        gbc.gridx = 1;
        txtPlayer1Name = new JTextField(15);
        txtPlayer1Name.setFont(new Font("Arial", Font.PLAIN, 14));
        multiplayerSetupPanel.add(txtPlayer1Name, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        multiplayerSetupPanel.add(createStyledLabel("Nombre del Jugador 2:"), gbc);
        gbc.gridx = 1;
        txtPlayer2Name = new JTextField(15);
        txtPlayer2Name.setFont(new Font("Arial", Font.PLAIN, 14));
        multiplayerSetupPanel.add(txtPlayer2Name, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnStartMultiplayer = createStyledButton("Iniciar Multijugador");
        btnStartMultiplayer.addActionListener(e -> startMultiplayerGame());
        multiplayerSetupPanel.add(btnStartMultiplayer, gbc);

        JButton btnBackToMenu = createStyledButton("Volver al Menú");
        btnBackToMenu.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        gbc.gridy = 4;
        multiplayerSetupPanel.add(btnBackToMenu, gbc);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 128, 0)); 
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
        button.setPreferredSize(new Dimension(200, 40)); 
        return button;
    }

    // --- Integración de la lógica del juego ---

    private void populateCategories() {
        cmbCategorias.removeAllItems();
        Set<String> categories = gestorExcel.getCategorias();
        for (String cat : categories) {
            cmbCategorias.addItem(cat.toUpperCase());
        }
        if (cmbCategorias.getItemCount() > 0) {
            cmbCategorias.setSelectedIndex(0); 
            }
    }

    private void showGamePanel(boolean isMultiplayer) {
        this.isMultiplayerMode = isMultiplayer;

        // Restablecer las puntuaciones de los jugadores para un juego individual o una nueva configuración de juego multijugador.
        if (!isMultiplayerMode || jugadores == null || jugadores.isEmpty()) {
            lblPlayer1Score.setText("P1: 0");
            lblPlayer2Score.setText("P2: 0");
            lblCurrentPlayer.setText("Jugador Actual: -");
            jugadores = new ArrayList<>(); 
        }

        // Ocultar/Mostrar etiquetas específicas del modo multijugador
        lblPlayer1Score.setVisible(isMultiplayerMode);
        lblPlayer2Score.setVisible(isMultiplayerMode);
        lblCurrentPlayer.setVisible(isMultiplayerMode);
        lblCurrentGamePoints.setVisible(true); 

        cardLayout.show(mainPanel, "Game");
        populateCategories(); 
        resetGameStateForNewRound(); 
    }

    private void resetGameStateForNewRound() {
        // Detener cualquier temporizador en funcionamiento
        if (guiTimer != null) {
            guiTimer.stop();
        }

        // Reinicializar juego para empezar de cero cada ronda/partida.
        // Pasar la instancia gestorExcel existente.
        juego = new JuegoAdivinanza(gestorExcel);

        lblPista.setText("PISTA: ");
        lblProgresoPalabra.setText("Progreso: _______");
        lblIntentosRestantes.setText("Intentos restantes: 6");
        lblTimer.setText("Tiempo: 60s");
        lblCurrentGamePoints.setText("Puntos de esta ronda: 0"); 
        txtEntrada.setText("");
        btnEnviar.setEnabled(true);
        cmbCategorias.setEnabled(true); 
        txtEntrada.setEnabled(true); 

        if (isMultiplayerMode && jugadores != null && !jugadores.isEmpty()) {
            lblCurrentPlayer.setText("Turno de: " + jugadores.get(currentPlayerIndex).getNombre());
            showMessage("Ronda " + currentRound + " - Turno de " + jugadores.get(currentPlayerIndex).getNombre() + ".");
        } else {
            lblCurrentPlayer.setText("Jugador Actual: (Un Jugador)");
            showMessage("Selecciona una categoría y haz clic en 'Nuevo Juego'.");
        }
    }

    private void iniciarNuevoJuego() {
        String selectedCategory = (String) cmbCategorias.getSelectedItem();
        if (selectedCategory == null || selectedCategory.isEmpty()) {
            showMessage("Por favor, selecciona una categoría válida.");
            return;
        }

        // Restablecer el estado actual del juego antes de comenzar uno nuevo.
        resetGameStateForNewRound();

        juego.iniciarJuego(selectedCategory.toLowerCase());

        // Comprueba si una palabra se ha cargado realmente.
        if (juego.getPalabra().isEmpty()) {
            showMessage("No se pudo iniciar el juego en la categoría: " + selectedCategory + ". Intenta otra.");
            btnEnviar.setEnabled(false); 
            txtEntrada.setEnabled(false);
            return;
        }

        lblPista.setText("PISTA: " + juego.getAdivinanza()); 
        updateGameDisplay(); 
        showMessage("Juego iniciado en la categoría: " + selectedCategory + ".");
        showMessage("INSTRUCCIONES: Puedes escribir una letra o la palabra completa.");

        startGUITimer();
        txtEntrada.requestFocusInWindow(); 
        cmbCategorias.setEnabled(false); 
    }

    private void updateGameDisplay() {
        lblProgresoPalabra.setText(juego.getProgresoActual().replace("", " ").trim());
        lblIntentosRestantes.setText("Intentos restantes: " + juego.getIntentosRestantes());
        lblCurrentGamePoints.setText("Puntos de esta ronda: " + juego.getPuntaje()); 
        updateScoresDisplay(); 
    }

    private void updateScoresDisplay() {
        if (isMultiplayerMode && jugadores != null && jugadores.size() == 2) {
            lblPlayer1Score.setText(jugadores.get(0).getNombre() + ": " + jugadores.get(0).getPuntaje());
            lblPlayer2Score.setText(jugadores.get(1).getNombre() + ": " + jugadores.get(1).getPuntaje());
        }
    }

    private void processGuess() {
        String entrada = txtEntrada.getText().trim().toLowerCase();
        txtEntrada.setText("");

        if (!btnEnviar.isEnabled()) { 
            return;
        }

        if (entrada.isEmpty()) {
            showMessage("Por favor, introduce una letra o la palabra completa.");
            return;
        }

        if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
            char guessChar = entrada.charAt(0);
            boolean alreadyGuessed = juego.getProgresoActual().indexOf(guessChar) != -1 ||
                                      !juego.getPalabra().contains(String.valueOf(guessChar)) &&
                                      (juego.getIntentosRestantes() == 6); 

            juego.adivinarLetra(guessChar);

            if (juego.getProgresoActual().indexOf(guessChar) != -1) { 
                showMessage("¡Correcto! Letra '" + guessChar + "' encontrada. (+10 puntos)");
            } else {
                showMessage("Incorrecto. La letra '" + guessChar + "' no está en la palabra. (-1 intento)");
            }
        } else {
            boolean correctWord = juego.verificarPalabraCompleta(entrada);
            juego.adivinarPalabraCompleta(entrada); 
            if (correctWord) {
                showMessage("¡Excelente! Has adivinado la palabra completa. (+50 puntos)");
            } else {
                showMessage("Palabra incorrecta. (-1 intento)");
            }
        }

        updateGameDisplay();
        checkGameEnd();
    }

    private void checkGameEnd() {
        if (juego.haGanado()) {
            showMessage("¡HAS GANADO! La palabra era: " + juego.getPalabra().toUpperCase());
            showMessage("Puntuación final de esta ronda: " + juego.getPuntaje());
            endGameTurn(true); 
        } else if (juego.haPerdido()) {
            showMessage("¡HAS PERDIDO! La palabra era: " + juego.getPalabra().toUpperCase());

            // Mostrar la palabra automáticamente si se pierde
            lblProgresoPalabra.setText(juego.getPalabra().toUpperCase().replace("", " ").trim());
            showMessage("Puntuación final de esta ronda: " + juego.getPuntaje()); 
            endGameTurn(false); 
        }
    }

    private void endGameTurn(boolean won) {
        btnEnviar.setEnabled(false); 
        txtEntrada.setEnabled(false);
        if (guiTimer != null) {
            guiTimer.stop(); 
        }
        cmbCategorias.setEnabled(true); 

        if (isMultiplayerMode) {
            Jugador currentPlayer = jugadores.get(currentPlayerIndex);
            if (won) {
                currentPlayer.sumarPuntos(juego.getPuntaje()); 
                showMessage(currentPlayer.getNombre() + " ha terminado su turno. ¡Has ganado la ronda! Puntuación total: " + currentPlayer.getPuntaje());
            } else {
                // No points added if lost
                showMessage(currentPlayer.getNombre() + " ha terminado su turno. No has adivinado la palabra. Puntuación total: " + currentPlayer.getPuntaje());
            }
            updateScoresDisplay(); 

            currentPlayerIndex = (currentPlayerIndex + 1) % jugadores.size();
            if (currentPlayerIndex == 0) {
                currentRound++;
                if (currentRound > 3) { 
                    showMessage("--- ¡JUEGO MULTIJUGADOR TERMINADO! ---");
                    displayMultiplayerResults();
                    return;
                }
                showMessage("--- Iniciando Ronda " + currentRound + " ---");
            }
            showMessage(jugadores.get(currentPlayerIndex).getNombre() + ", es tu turno.");
            resetGameStateForNewRound(); 
        } else { 
            showMessage("Haz clic en 'Nuevo Juego' para jugar de nuevo.");
        }
    }

    private void startGUITimer() {
        timeLeft = 60; 
        lblTimer.setText("Tiempo: " + timeLeft + "s");

        if (guiTimer != null) {
            guiTimer.stop(); 
        }

        guiTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                lblTimer.setText("Tiempo: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    guiTimer.stop();
                    showMessage("⏰ ¡Tiempo agotado!");
                    juego.setTiempoAgotado(true); 
                    checkGameEnd(); 
                }
            }
        });
        guiTimer.start();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showAcercaDe() {
        String acercaDeText = "--- Acerca de ---\n" +
                              "Equipo: PROJECT MANHATTAN\n" +
                              "Integrantes: SHAROON JAIMES, JOHAN MORA, JUAN VELASQUEZ\n" +
                              "Eslogan: ¡Codificamos tu diversion!";
        JOptionPane.showMessageDialog(this, acercaDeText, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startMultiplayerGame() {
        String player1Name = txtPlayer1Name.getText().trim();
        String player2Name = txtPlayer2Name.getText().trim();

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa los nombres de ambos jugadores.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        jugadores = new ArrayList<>();
        jugadores.add(new Jugador(player1Name));
        jugadores.add(new Jugador(player2Name));
        currentPlayerIndex = 0;
        currentRound = 1;

        lblCurrentPlayer.setText("Turno de: " + jugadores.get(currentPlayerIndex).getNombre());
        updateScoresDisplay();

        JOptionPane.showMessageDialog(this, "Modo Multijugador iniciado: " + player1Name + " vs " + player2Name + ".\n" +
                                        "El primer turno es para " + player1Name + ".", "Multijugador", JOptionPane.INFORMATION_MESSAGE);

        showGamePanel(true); 
        showMessage("¡Prepárense para la Ronda 1!");
    }

    private void displayMultiplayerResults() {
        Jugador p1 = jugadores.get(0);
        Jugador p2 = jugadores.get(1);

        String results = "--- RESULTADOS FINALES ---\n" +
                         p1.getNombre() + ": " + p1.getPuntaje() + " puntos\n" +
                         p2.getNombre() + ": " + p2.getPuntaje() + " puntos\n\n";

        if (p1.getPuntaje() > p2.getPuntaje()) {
            results += "🏆 " + p1.getNombre() + " GANA EL JUEGO!";
        } else if (p2.getPuntaje() > p1.getPuntaje()) {
            results += "🏆 " + p2.getNombre() + " GANA EL JUEGO!";
        } else {
            results += "🤝 ¡Es un EMPATE!";
        }

        JOptionPane.showMessageDialog(this, results, "Resultados Finales Multijugador", JOptionPane.INFORMATION_MESSAGE);

        // Restablecer el estado multijugador para permitir iniciar una nueva partida.
        isMultiplayerMode = false;
        jugadores = null;
        currentPlayerIndex = 0;
        currentRound = 1;
        cardLayout.show(mainPanel, "Menu");
    }

    private void returnToMainMenu() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que quieres volver al menú principal? Se perderá el progreso actual del juego.",
            "Volver al Menú", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (guiTimer != null) {
                guiTimer.stop(); 
            }
            // Restablecer todas las variables de estado relacionadas con el juego para volver al menú sin problemas.
            isMultiplayerMode = false;
            jugadores = null;
            currentPlayerIndex = 0;
            currentRound = 1;
            // Crea una nueva instancia de JuegoAdivinanza para restablecer completamente la lógica del juego.
            juego = new JuegoAdivinanza(gestorExcel);
            cardLayout.show(mainPanel, "Menu");
            showMessage("Has vuelto al menú principal.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JuegoAdivinanzaGUI().setVisible(true);
        });
    }
}