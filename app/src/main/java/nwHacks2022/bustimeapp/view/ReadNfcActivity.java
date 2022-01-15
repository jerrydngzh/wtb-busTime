package nwHacks2022.bustimeapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Toast;

import nwHacks2022.bustimeapp.R;

/*
https://itnext.io/how-to-use-nfc-tags-with-android-studio-detect-read-and-write-nfcs-42f1d60b033
 */
public class ReadNfcActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_nfc);
        pendingIntent = makePendingIntent(ReadNfcActivity.this);

        initializeNFC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        assert nfcAdapter != null;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    private void initializeNFC() {
        //initialize adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "NO NFC FUNCTIONALITY", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ReadNfcActivity.class);
    }

    public static PendingIntent makePendingIntent(Context context) {
        Intent intent = new Intent(context, ReadNfcActivity.class);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}