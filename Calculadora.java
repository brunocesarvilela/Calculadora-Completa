package calculadora;

/**
 * Classe responsável pela LÓGICA da calculadora.
 * Separar a lógica da interface é uma boa prática chamada de "Separação de Responsabilidades".
 */
public class Calculadora {

    /**
     * Realiza uma operação matemática entre dois números.
     *
     * @param a         Primeiro número
     * @param b         Segundo número
     * @param operacao  O símbolo da operação: +, -, *, /
     * @return          O resultado da operação
     * @throws ArithmeticException se tentar dividir por zero
     * @throws IllegalArgumentException se a operação for inválida
     */
    public double calcular(double a, double b, String operacao) {
        switch (operacao) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) {
                    // Não é possível dividir por zero!
                    throw new ArithmeticException("Não é possível dividir por zero.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Operação desconhecida: " + operacao);
        }
    }

    /**
     * Formata o resultado para exibição:
     * - Se for um número inteiro (ex: 4.0), mostra como "4"
     * - Se tiver decimais (ex: 3.5), mostra normalmente
     */
    public String formatarResultado(double resultado) {
        if (resultado == (long) resultado) {
            return String.valueOf((long) resultado);
        } else {
            // Limita a 10 casas decimais para não poluir o display
            return String.format("%.10g", resultado).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }
}
