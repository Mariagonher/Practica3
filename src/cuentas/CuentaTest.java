package cuentas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CuentaTest {

    @Test
    void testCrearCuentaYSaldoInicial() {
        Cuenta cuenta = new Cuenta("12345", "María", 50.0);
        assertEquals(50.0, cuenta.getSaldo(), 0.001);
    }
    @Test
    void testIngresar() {
        Cuenta cuenta = new Cuenta("12345", "María", 50.0);
        cuenta.ingresar(100.0);
        assertEquals(150.0, cuenta.getSaldo(), 0.001);
    }
    @Test
    void testReintegroValido() {
        Cuenta cuenta = new Cuenta("12345", "María", 50.0);
        cuenta.reintegro(30.0);
        assertEquals(20.0, cuenta.getSaldo(), 0.001);
    }
    @Test
    void test0014() {
        // Inicialización
        Cuenta cuenta12345 = new Cuenta("12345", "Titular 1", 50.0);
        Cuenta cuenta67890 = new Cuenta("67890", "Titular 2", 0.0);

        // Instrucciones y operaciones
        cuenta12345.reintegro(200.0); // 1. Reintegro de 200€ (saldo queda en -150)
        
        try {
            cuenta67890.reintegro(350.0); // 2. Reintegro de 350€ (saldo queda en -350, permitido por el límite de -500)
        } catch (Exception e) {
            // Control por si lanza excepción
        }

        cuenta12345.ingresar(100.0);    // 3. Ingreso de 100€ (saldo queda en -50)

        try {
            cuenta67890.reintegro(200.0); // 4. Reintegro de 200€ en 67890 -> Intentaría bajar a -550, supera el límite de -500! Debe fallar / dar error
        } catch (RuntimeException e) {
            // Se captura el error de fondos insuficientes esperado
        }

        cuenta67890.reintegro(150.0);   // 5. Reintegro de 150€ (desde -350 baja a -500, permitido exacto)
        cuenta12345.reintegro(200.0);   // 6. Reintegro de 200€ (desde -50 baja a -250)
        cuenta67890.ingresar(50.0);     // 7. Ingreso de 50€ (desde -500 sube a -450)

        try {
            cuenta67890.reintegro(100.0); // 8. Reintegro de 100€ en 67890 -> Intentaría bajar a -550, supera el límite de -500! Debe fallar / dar error
        } catch (RuntimeException e) {
            // Se captura el error de fondos insuficientes esperado
        }

        // Resultados esperados finales según el caso Test0014
        assertEquals(-250.0, cuenta12345.getSaldo(), 0.001);
        assertEquals(-450.0, cuenta67890.getSaldo(), 0.001);
    }

}
