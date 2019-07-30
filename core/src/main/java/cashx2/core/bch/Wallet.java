package cashx2.core.bch;

import com.softwareverde.bitcoin.address.AddressInflater;
import com.softwareverde.bitcoin.secp256k1.key.PrivateKey;
import com.softwareverde.constable.list.List;
import com.softwareverde.constable.list.immutable.ImmutableArrayListBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Wallet {
    private static ArrayList<PrivateKey> privateKeys ;
    private static Wallet instance;
    private Wallet(){
        //Nothing
    }
    public static synchronized Wallet getInstance(){
        if(instance == null){
            instance = new Wallet();
        }
        return instance;
    }

    public static void addPrivateKey(PrivateKey privateKey){
        privateKeys.add(privateKey);
    }

    public static void addPrivateKeys(PrivateKey[] privateKeysArray){
        ArrayList<PrivateKey> pKeys = (ArrayList<PrivateKey>) Arrays.asList(privateKeysArray);
        privateKeys.addAll(pKeys);
    }
    public static String[] getAddresses(){
        String[] addresses = {};
        AddressInflater addressInflater = new AddressInflater();
        for(int i = 0; i <= privateKeys.size(); i++){
            PrivateKey pk = privateKeys.get(i);
            addresses[i] = addressInflater.compressedFromPrivateKey(pk).toBase58CheckEncoded();
        }
        return addresses;
    }
}
