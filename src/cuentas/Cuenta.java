package cuentas;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numero;
    private String titular;
    private double saldo;
    private List<Movimiento> mMovimientos;

    // Constructor
    public Cuenta(String numero, String titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.mMovimientos = new ArrayList<>();
    }

    // Método que necesita el test actual
    public double getSaldo() {
        return saldo;
    }

    // Métodos indicados en el diagrama (los dejaremos listos para los siguientes pasos)
    public void ingreso(double x) {
        // Implementaremos en el siguiente ciclo TDD
    }
    public void ingresar(double x) {
        if (x > 0) {
            saldo += x;
            mMovimientos.add(new Movimiento(x, Movimiento.Signo.D, "Ingreso"));
        }
    }
    public void reintegro(double x) {
        if (x > 0) {
            // Comprobamos la regla del límite de descubierto de 500€ (-500.0)
            if ((saldo - x) >= -500.0) {
                saldo -= x; // <-- ¡Aquí debe restar (-=)!
                mMovimientos.add(new Movimiento(x, Movimiento.Signo.H, "Reintegro"));
            } else {
                throw new RuntimeException("Fondos insuficientes");
            }
        }
    }
}