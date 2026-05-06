package calculadora;

import javax.swing.SwingUtilities;

/**
 * Ponto de entrada do programa.
 *
 * O método main() é SEMPRE onde o Java começa a executar.
 * SwingUtilities.invokeLater garante que a interface gráfica
 * seja criada na thread correta (a "Event Dispatch Thread").
 */
public class Main {

    public static void main(String[] args) {

        // Boa prática: criar componentes Swing sempre dentro do invokeLater
        SwingUtilities.invokeLater(() -> {
            CalculadoraGUI janela = new CalculadoraGUI();
            janela.setVisible(true); // Torna a janela visível
        });
    }
}
