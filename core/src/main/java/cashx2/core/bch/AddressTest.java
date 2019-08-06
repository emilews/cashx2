package cashx2.core.bch;

import org.bitcoinj.crypto.MnemonicCode;

import java.io.IOException;
import java.util.List;

public class AddressTest {

    public static void main(String[] args) throws IOException {
        MnemonicCode m = new MnemonicCode();
        List<String> w = m.getWordList();
        List<String> mnemo = null;
        int rand = 0;
        for(int i = 0; i <= 20; i++){
            rand = (int)Math.random();
            mnemo.add(w.get(rand));
        }
    }
}
