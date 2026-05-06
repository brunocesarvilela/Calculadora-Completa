package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe da Interface Gráfica (GUI) da Calculadora.
 *
 * Conceitos Java usados aqui:
 * - JFrame: a janela principal
 * - JPanel: painéis para organizar os componentes
 * - JButton: os botões
 * - JLabel: o display de texto
 * - ActionListener: detecta cliques nos botões
 * - GridLayout / BorderLayout: gerenciadores de layout
 */
public class CalculadoraGUI extends JFrame {

    // ===== COMPONENTES VISUAIS =====
    private JLabel displayPrincipal;   // Mostra o número atual ou resultado
    private JLabel displayHistorico;   // Mostra a operação em andamento (ex: "8 + ")

    // ===== ESTADO DA CALCULADORA =====
    private double primeiroNumero = 0;   // Guarda o primeiro número digitado
    private String operacaoAtual = "";   // Guarda a operação escolhida (+, -, *, /)
    private boolean aguardandoSegundoNumero = false; // Indica se esperamos o 2º número
    private boolean acabouDeCalcular = false;        // Indica se acabamos de apertar "="

    // ===== LÓGICA =====
    private Calculadora calculadora = new Calculadora();

    // ===== CORES DO TEMA =====
    private final Color COR_FUNDO         = new Color(28, 28, 30);
    private final Color COR_DISPLAY       = new Color(28, 28, 30);
    private final Color COR_BTN_NUMERO    = new Color(58, 58, 60);
    private final Color COR_BTN_OPERACAO  = new Color(255, 159, 10);
    private final Color COR_BTN_FUNCAO    = new Color(72, 72, 74);
    private final Color COR_BTN_IGUAL     = new Color(255, 159, 10);
    private final Color COR_TEXTO         = Color.WHITE;
    private final Color COR_TEXTO_ESCURO  = new Color(28, 28, 30);

    public CalculadoraGUI() {
        configurarJanela();
        criarComponentes();
    }

    /**
     * Configura a janela principal (JFrame).
     */
    private void configurarJanela() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fecha o programa ao fechar a janela
        setResizable(false);                              // Não permite redimensionar
        setBackground(COR_FUNDO);
        getContentPane().setBackground(COR_FUNDO);
    }

    /**
     * Cria e organiza todos os componentes visuais.
     */
    private void criarComponentes() {
        // Painel principal com espaçamento nas bordas
        JPanel painelPrincipal = new JPanel(new BorderLayout(0, 0));
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ----- DISPLAY -----
        JPanel painelDisplay = criarDisplay();
        painelPrincipal.add(painelDisplay, BorderLayout.NORTH);

        // ----- BOTÕES -----
        JPanel painelBotoes = criarBotoes();
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null); // Centraliza na tela
    }

    /**
     * Cria o painel do display (onde os números aparecem).
     */
    private JPanel criarDisplay() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(COR_DISPLAY);
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 16, 8));

        // Linha do histórico (ex: "8 + ")
        displayHistorico = new JLabel(" ");
        displayHistorico.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
        displayHistorico.setForeground(new Color(160, 160, 165));
        displayHistorico.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Linha principal (número atual ou resultado)
        displayPrincipal = new JLabel("0");
        displayPrincipal.setFont(new Font("SF Pro Display", Font.PLAIN, 64));
        displayPrincipal.setForeground(COR_TEXTO);
        displayPrincipal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        painel.add(displayHistorico);
        painel.add(displayPrincipal);

        // Define largura mínima do display
        painel.setPreferredSize(new Dimension(320, 110));

        return painel;
    }

    /**
     * Cria o painel com todos os botões.
     *
     * Layout da calculadora:
     *  [AC]  [+/-]  [%]   [÷]
     *  [7]   [8]    [9]   [×]
     *  [4]   [5]    [6]   [−]
     *  [1]   [2]    [3]   [+]
     *  [  0      ]  [.]   [=]
     */
    private JPanel criarBotoes() {
        JPanel painel = new JPanel(new GridLayout(5, 4, 10, 10));
        painel.setBackground(COR_FUNDO);

        // Linha 1: funções especiais + operação
        painel.add(criarBotao("AC",  COR_BTN_FUNCAO,   COR_TEXTO));
        painel.add(criarBotao("+/-", COR_BTN_FUNCAO,   COR_TEXTO));
        painel.add(criarBotao("%",   COR_BTN_FUNCAO,   COR_TEXTO));
        painel.add(criarBotao("÷",   COR_BTN_OPERACAO, COR_TEXTO_ESCURO));

        // Linha 2: números 7-9 + operação
        painel.add(criarBotao("7",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("8",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("9",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("×",   COR_BTN_OPERACAO, COR_TEXTO_ESCURO));

        // Linha 3: números 4-6 + operação
        painel.add(criarBotao("4",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("5",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("6",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("−",   COR_BTN_OPERACAO, COR_TEXTO_ESCURO));

        // Linha 4: números 1-3 + operação
        painel.add(criarBotao("1",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("2",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("3",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("+",   COR_BTN_OPERACAO, COR_TEXTO_ESCURO));

        // Linha 5: 0 (duplo), ponto decimal, igual
        painel.add(criarBotaoZero());
        painel.add(criarBotao(".",   COR_BTN_NUMERO,   COR_TEXTO));
        painel.add(criarBotao("=",   COR_BTN_IGUAL,    COR_TEXTO_ESCURO));

        return painel;
    }

    /**
     * Cria um botão padrão com estilo arredondado.
     */
    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton botao = new JButton(texto) {
            // Sobrescrevemos o paintComponent para desenhar o botão arredondado
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Ativa o antialiasing (suavização das bordas)
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Cor de fundo (mais clara quando o mouse está em cima)
                if (getModel().isPressed()) {
                    g2.setColor(corFundo.brighter());
                } else if (getModel().isRollover()) {
                    g2.setColor(corFundo.brighter().brighter());
                } else {
                    g2.setColor(corFundo);
                }

                // Desenha o fundo arredondado
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        botao.setFont(new Font("SF Pro Display", Font.PLAIN, 22));
        botao.setForeground(corTexto);
        botao.setContentAreaFilled(false);  // Desativa o fundo padrão
        botao.setBorderPainted(false);      // Remove a borda padrão
        botao.setFocusPainted(false);       // Remove o destaque de foco
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(70, 70));
        botao.setOpaque(false);

        // Registra o evento de clique
        botao.addActionListener(e -> processarClique(texto));

        return botao;
    }

    /**
     * Cria o botão "0" que ocupa duas colunas.
     */
    private JButton criarBotaoZero() {
        JButton botao = new JButton("0") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(COR_BTN_NUMERO.brighter());
                } else if (getModel().isRollover()) {
                    g2.setColor(COR_BTN_NUMERO.brighter().brighter());
                } else {
                    g2.setColor(COR_BTN_NUMERO);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        botao.setFont(new Font("SF Pro Display", Font.PLAIN, 22));
        botao.setForeground(COR_TEXTO);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setBorder(BorderFactory.createEmptyBorder(0, 26, 0, 0));
        botao.setOpaque(false);

        // O botão 0 precisa ocupar 2 colunas no GridLayout
        // Fazemos isso adicionando um painel que contém o botão
        botao.addActionListener(e -> processarClique("0"));
        return botao;
    }

    // =========================================================
    // LÓGICA DE PROCESSAMENTO DOS CLIQUES
    // =========================================================

    /**
     * Método central que decide o que fazer quando um botão é clicado.
     */
    private void processarClique(String valor) {
        switch (valor) {
            case "AC":
                limparTudo();
                break;
            case "+/-":
                inverterSinal();
                break;
            case "%":
                aplicarPorcentagem();
                break;
            case "+":
            case "−":
            case "×":
            case "÷":
                selecionarOperacao(valor);
                break;
            case "=":
                calcularResultado();
                break;
            case ".":
                adicionarDecimal();
                break;
            default:
                // É um dígito numérico (0-9)
                digitarNumero(valor);
        }
    }

    /**
     * Adiciona um dígito ao número exibido no display.
     */
    private void digitarNumero(String digito) {
        String displayAtual = displayPrincipal.getText();

        // Se acabou de calcular ou está aguardando novo número, começa do zero
        if (acabouDeCalcular || aguardandoSegundoNumero) {
            displayPrincipal.setText(digito);
            acabouDeCalcular = false;
            aguardandoSegundoNumero = false;
        } else {
            // Limita a 9 dígitos para não ultrapassar o display
            if (displayAtual.replace("-", "").replace(".", "").length() >= 9) return;

            // Substitui "0" inicial (mas não "0.")
            if (displayAtual.equals("0")) {
                displayPrincipal.setText(digito);
            } else {
                displayPrincipal.setText(displayAtual + digito);
            }
        }
    }

    /**
     * Adiciona um ponto decimal ao número.
     */
    private void adicionarDecimal() {
        String displayAtual = displayPrincipal.getText();

        if (aguardandoSegundoNumero) {
            displayPrincipal.setText("0.");
            aguardandoSegundoNumero = false;
            return;
        }

        // Só adiciona o ponto se ainda não tiver um
        if (!displayAtual.contains(".")) {
            displayPrincipal.setText(displayAtual + ".");
        }
    }

    /**
     * Guarda o primeiro número e a operação escolhida.
     */
    private void selecionarOperacao(String operacao) {
        // Converte símbolo visual para símbolo matemático
        String opMatematica = converterOperacao(operacao);

        double numeroAtual = Double.parseDouble(displayPrincipal.getText());

        // Se já temos uma operação pendente, calcula antes de guardar a nova
        if (!operacaoAtual.isEmpty() && !aguardandoSegundoNumero) {
            try {
                double resultado = calculadora.calcular(primeiroNumero, numeroAtual, operacaoAtual);
                displayPrincipal.setText(calculadora.formatarResultado(resultado));
                primeiroNumero = resultado;
            } catch (ArithmeticException ex) {
                displayPrincipal.setText("Erro");
                limparEstado();
                return;
            }
        } else {
            primeiroNumero = numeroAtual;
        }

        operacaoAtual = opMatematica;
        aguardandoSegundoNumero = true;
        acabouDeCalcular = false;

        // Atualiza o histórico no display
        displayHistorico.setText(calculadora.formatarResultado(primeiroNumero) + " " + operacao);
    }

    /**
     * Executa o cálculo quando "=" é pressionado.
     */
    private void calcularResultado() {
        if (operacaoAtual.isEmpty()) return;

        double segundoNumero = Double.parseDouble(displayPrincipal.getText());

        try {
            double resultado = calculadora.calcular(primeiroNumero, segundoNumero, operacaoAtual);
            String resultadoStr = calculadora.formatarResultado(resultado);

            // Atualiza o histórico com a operação completa
            displayHistorico.setText(
                calculadora.formatarResultado(primeiroNumero) + " " +
                converterParaVisual(operacaoAtual) + " " +
                calculadora.formatarResultado(segundoNumero) + " ="
            );

            displayPrincipal.setText(resultadoStr);
            primeiroNumero = resultado;
            operacaoAtual = "";
            acabouDeCalcular = true;

        } catch (ArithmeticException ex) {
            displayPrincipal.setText("Erro: ÷ 0");
            displayHistorico.setText(" ");
            limparEstado();
        }
    }

    /**
     * Limpa tudo e volta ao estado inicial.
     */
    private void limparTudo() {
        displayPrincipal.setText("0");
        displayHistorico.setText(" ");
        limparEstado();
    }

    private void limparEstado() {
        primeiroNumero = 0;
        operacaoAtual = "";
        aguardandoSegundoNumero = false;
        acabouDeCalcular = false;
    }

    /**
     * Inverte o sinal do número (positivo ↔ negativo).
     */
    private void inverterSinal() {
        double numero = Double.parseDouble(displayPrincipal.getText());
        displayPrincipal.setText(calculadora.formatarResultado(-numero));
    }

    /**
     * Converte o número para porcentagem (divide por 100).
     */
    private void aplicarPorcentagem() {
        double numero = Double.parseDouble(displayPrincipal.getText());
        displayPrincipal.setText(calculadora.formatarResultado(numero / 100));
    }

    // Converte símbolo visual (÷, ×, −) para símbolo matemático (/, *, -)
    private String converterOperacao(String op) {
        switch (op) {
            case "÷": return "/";
            case "×": return "*";
            case "−": return "-";
            default:  return op;
        }
    }

    // Converte símbolo matemático de volta para visual
    private String converterParaVisual(String op) {
        switch (op) {
            case "/": return "÷";
            case "*": return "×";
            case "-": return "−";
            default:  return op;
        }
    }
}
