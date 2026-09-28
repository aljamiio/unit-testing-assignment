package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    // W01: Default constructor should create wallet with 0 balance
    @Test
    void testDefaultConstructor() {
        Wallet wallet = new Wallet();

        assertEquals(0.0, wallet.getBalance());
    }

    // W02: Constructor with initial balance
    @Test
    void testInitialBalance() {
        Wallet wallet = new Wallet(100.0);

        assertEquals(100.0, wallet.getBalance());
    }

    // W03: Adding a positive amount
    @Test
    void testAddFunds() {
        Wallet wallet = new Wallet(100.0);

        wallet.addFunds(50.0);

        assertEquals(150.0, wallet.getBalance());
    }

    // W04: Adding zero should throw InvalidAmountException
    @Test
    void testAddZeroFunds() {
        Wallet wallet = new Wallet(100.0);

        assertThrows(
                InvalidAmountException.class,
                () -> wallet.addFunds(0.0)
        );
    }

    // W05: Adding negative amount should throw InvalidAmountException
    @Test
    void testAddNegativeFunds() {
        Wallet wallet = new Wallet(100.0);

        assertThrows(
                InvalidAmountException.class,
                () -> wallet.addFunds(-10.0)
        );
    }

    // W06: Deducting a valid amount
    @Test
    void testDeductFunds() {
        Wallet wallet = new Wallet(100.0);

        wallet.deductFunds(40.0);

        assertEquals(60.0, wallet.getBalance());
    }

    // W07: Deducting the exact available balance
    @Test
    void testDeductExactBalance() {
        Wallet wallet = new Wallet(100.0);

        wallet.deductFunds(100.0);

        assertEquals(0.0, wallet.getBalance());
    }

    // W08: Deducting more than available balance
    @Test
    void testDeductInsufficientFunds() {
        Wallet wallet = new Wallet(100.0);

        assertThrows(
                InsufficientFundsException.class,
                () -> wallet.deductFunds(101.0)
        );

        // Balance should remain unchanged
        assertEquals(100.0, wallet.getBalance());
    }

    // W09: Deducting zero
    @Test
    void testDeductZeroFunds() {
        Wallet wallet = new Wallet(100.0);

        assertThrows(
                InvalidAmountException.class,
                () -> wallet.deductFunds(0.0)
        );
    }

    // W10: Deducting negative amount
    @Test
    void testDeductNegativeFunds() {
        Wallet wallet = new Wallet(100.0);

        assertThrows(
                InvalidAmountException.class,
                () -> wallet.deductFunds(-20.0)
        );
    }

    // W11: Successful transfer between two wallets
    @Test
    void testTransferFunds() {
        Wallet sender = new Wallet(100.0);
        Wallet receiver = new Wallet(50.0);

        sender.transferFunds(receiver, 30.0);

        assertEquals(70.0, sender.getBalance());
        assertEquals(80.0, receiver.getBalance());
    }

    // W12: Transfer more than sender's balance
    @Test
    void testTransferInsufficientFunds() {
        Wallet sender = new Wallet(100.0);
        Wallet receiver = new Wallet(50.0);

        assertThrows(
                InsufficientFundsException.class,
                () -> sender.transferFunds(receiver, 101.0)
        );

        // Neither wallet should change
        assertEquals(100.0, sender.getBalance());
        assertEquals(50.0, receiver.getBalance());
    }

    // W13: Transfer zero
    @Test
    void testTransferZeroFunds() {
        Wallet sender = new Wallet(100.0);
        Wallet receiver = new Wallet(50.0);

        assertThrows(
                InvalidAmountException.class,
                () -> sender.transferFunds(receiver, 0.0)
        );
    }

    // W14: Transfer negative amount
    @Test
    void testTransferNegativeFunds() {
        Wallet sender = new Wallet(100.0);
        Wallet receiver = new Wallet(50.0);

        assertThrows(
                InvalidAmountException.class,
                () -> sender.transferFunds(receiver, -10.0)
        );
    }
}
