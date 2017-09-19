package org.fedorahosted.freeotp.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.Toast;
import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.camera.CameraController;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;
import org.fedorahosted.freeotp.MainActivity;
import org.fedorahosted.freeotp.R;

public class BarCodeActivity extends Activity
{
    private TextureView textureView;
    private ScannerLiveView camera;
    private CameraController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        camera = (ScannerLiveView) findViewById(R.id.liveView);

        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener()
        {
            @Override
            public void onScannerStarted(ScannerLiveView scanner)
            {
                Toast.makeText(BarCodeActivity.this,"Scanner Started",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner)
            {
                Toast.makeText(BarCodeActivity.this,"Scanner Stopped",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err)
            {
                Toast.makeText(BarCodeActivity.this,"Scanner Error: " + err.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data)
            {
                Toast.makeText(BarCodeActivity.this, data, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("data", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.5);
        camera.setDecoder(decoder);
        camera.startScanner();
    }

    @Override
    protected void onPause()
    {
        camera.stopScanner();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }



}
