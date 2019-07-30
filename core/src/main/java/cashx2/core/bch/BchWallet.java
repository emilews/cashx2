package cashx2.core.bch;

import com.softwareverde.bitcoin.chain.time.MutableMedianBlockTime;
import com.softwareverde.bitcoin.wallet.Wallet;

public class BchWallet extends Wallet {
    private static com.softwareverde.bitcoin.wallet.Wallet instance;
    public BchWallet(){
        instance = new com.softwareverde.bitcoin.wallet.Wallet(new MutableMedianBlockTime());
    }
}
