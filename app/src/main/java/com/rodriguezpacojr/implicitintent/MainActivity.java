package com.rodriguezpacojr.implicitintent;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnCall, R.id.btnMail, R.id.btnSms, R.id.btnWeb, R.id.btnPhoto, R.id.btnMap, R.id.btnContacts,  R.id.btnSearch,  R.id.btnAlarm,  R.id.btnReminder,  R.id.btnInsertContact,  R.id.btnPlay})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                makeACall();
                break;

            case R.id.btnMail:
                sendEmail();
                break;

            case R.id.btnSms:
                sendSms();
                break;

            case R.id.btnWeb:
                Intent intWeb= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.itcelaya.edu.mx"));
                startActivity(intWeb);
                break;

            case R.id.btnPhoto:
                Intent intCam = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intCam);
                break;

            case R.id.btnMap:
                openMap();
                break;

           case R.id.btnContacts:
               Intent intCont = new Intent();
               intCont.setAction(Intent.ACTION_VIEW);
               intCont.setData(Uri.parse("content://contacts/people/"));
               startActivity(intCont);
                break;

            case R.id.btnSearch:
                search();
                break;

            case R.id.btnAlarm:
                setAlarm();
                break;

            case R.id.btnReminder:
                reminder();
                break;

            case R.id.btnInsertContact:
                insertContact();
                break;

            case R.id.btnPlay:
                play();
                break;
        }
    }

    private void makeACall() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Phone");
        editText.setInputType(2);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() < 10 && editText.getText().toString().length() > 0)
                    Toast.makeText(MainActivity.this, "Type 10 digits", Toast.LENGTH_SHORT).show();
                else if (!editText.getText().toString().isEmpty()) {
                    Intent intCall = new Intent(Intent.ACTION_CALL);
                    intCall.setData(Uri.parse("tel:"+editText.getText().toString()));

                    if (intCall.resolveActivity(getPackageManager()) != null)
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                            return;
                    startActivity(intCall);
                }
                else
                    Toast.makeText(MainActivity.this, "Set Phone", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void sendEmail() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Email");
        editText.setHint("Email");
        editText.setInputType(1);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    Intent intMail = new Intent(Intent.ACTION_SEND);
                    intMail.setType("text/plain");
                    intMail.putExtra(intMail.EXTRA_EMAIL, new String[]{editText.getText().toString()});

                    startActivity(intMail);
                }
                else
                    Toast.makeText(MainActivity.this, "Set Email", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void sendSms() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Phone");
        editText.setInputType(2);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() < 10 && editText.getText().toString().length() > 0)
                    Toast.makeText(MainActivity.this, "Type 10 digits", Toast.LENGTH_SHORT).show();
                else if (!editText.getText().toString().isEmpty()) {
                    Intent intSms = new Intent(Intent.ACTION_SENDTO);
                    intSms.setData(Uri.parse("sms: "+editText.getText().toString()));
                    intSms.putExtra("sms_body","Hello");
                    startActivity(intSms);
                }
                else
                    Toast.makeText(MainActivity.this, "Set Phone", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void openMap() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert2, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        final EditText editText2 = (EditText) mView.findViewById(R.id.editText2);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Map");
        editText.setHint("Latitude");
        editText2.setHint("Longitude");
        editText.setInputType(3);
        editText2.setInputType(3);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {
                    Intent intMap = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+editText.getText().toString()+","+editText2.getText().toString()));
                    startActivity(intMap);
                }
                else
                    Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void search() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Key Word");
        editText.setInputType(1);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    Intent intSearch= new Intent(Intent.ACTION_WEB_SEARCH );
                    intSearch.putExtra(SearchManager.QUERY, editText.getText().toString());
                    startActivity(intSearch);
                }
                else
                    Toast.makeText(MainActivity.this, "Type a word", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void play() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Artist");
        editText.setHint("Artist");
        editText.setInputType(1);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    Intent intPlay = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                    intPlay.putExtra(MediaStore.EXTRA_MEDIA_FOCUS,
                            MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE);
                    intPlay.putExtra(MediaStore.EXTRA_MEDIA_ARTIST, editText.getText().toString());
                    intPlay.putExtra(SearchManager.QUERY, editText.getText().toString());
                    if (intPlay.resolveActivity(getPackageManager()) != null)
                        startActivity(intPlay);
                }
                else
                    Toast.makeText(MainActivity.this, "Type an Artist", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void insertContact()  {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert3, null);

        TextView textView = (TextView) mView.findViewById(R.id.textView);

        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        final EditText editText2 = (EditText) mView.findViewById(R.id.editText2);
        final EditText editText3 = (EditText) mView.findViewById(R.id.editText3);

        textView.setText("Contact");
        editText.setHint("Name");
        editText2.setHint("Number");
        editText3.setHint("Email");

        editText.setInputType(1);
        editText2.setInputType(2);
        editText3.setInputType(1);

        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty() && !editText3.getText().toString().isEmpty()) {
                    Intent intInsC = new Intent(Intent.ACTION_INSERT);
                    intInsC.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    intInsC.putExtra(ContactsContract.Intents.Insert.NAME, editText.getText().toString());
                    intInsC.putExtra(ContactsContract.Intents.Insert.PHONE, editText2.getText().toString());
                    intInsC.putExtra(ContactsContract.Intents.Insert.EMAIL, editText3.getText().toString());
                    if (intInsC.resolveActivity(getPackageManager()) != null)
                        startActivity(intInsC);
                }
                else
                    Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void setAlarm() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert2, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        final EditText editText2 = (EditText) mView.findViewById(R.id.editText2);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Alarm");
        editText.setHint("Hour");
        editText2.setHint("Minutes");
        editText.setInputType(3);
        editText2.setInputType(3);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {
                    int hour = Integer.parseInt(editText.getText().toString());
                    int minutes = Integer.parseInt(editText2.getText().toString());
                    Intent intAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intAlarm.putExtra(AlarmClock.EXTRA_HOUR, hour);
                    intAlarm.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
                    startActivity(intAlarm);
                }
                else
                    Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void reminder() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_alert2, null);
        final EditText editText = (EditText) mView.findViewById(R.id.editText);
        final EditText editText2 = (EditText) mView.findViewById(R.id.editText2);
        TextView textView = (TextView) mView.findViewById(R.id.textView);
        textView.setText("Reminder");
        editText.setHint("Title");
        editText2.setHint("Location");
        editText.setInputType(1);
        editText2.setInputType(1);
        Button btnOK = (Button) mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {

                    Intent intRem= new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, editText.getText().toString())
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, editText2.getText().toString())
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 1000)
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 2000);
                    if (intRem.resolveActivity(getPackageManager()) != null)
                        startActivity(intRem);
                }
                else
                    Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 1);*/
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            System.out.println("************************************");
            System.out.println("CONTACTURI: "+contactUri);
            System.out.println("*************************************");
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                //textView.setText(number);

            }
        }
    }
}