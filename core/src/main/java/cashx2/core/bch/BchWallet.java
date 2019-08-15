package cashx2.core.bch;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChainGroup;

import java.util.List;

public class BchWallet{
    private static DeterministicSeed deterministicSeed ;
    private static KeyChainGroup keyChainGroup;
    private static NetworkParameters networkParameters = MainNetParams.get();


    private static BchWallet instance = null;

    public static synchronized BchWallet getInstance(String seed, List<String> wordlist){
        if(instance == null){
           deterministicSeed = new DeterministicSeed(seed.getBytes(), wordlist, 1000);
           keyChainGroup = new KeyChainGroup(networkParameters, deterministicSeed);
            instance = new BchWallet();
        }
        return instance;
    }

    private BchWallet(){
    }
}
