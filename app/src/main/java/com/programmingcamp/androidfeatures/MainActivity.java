package com.programmingcamp.androidfeatures;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnPlaySound, btnPreference, btnLoadImage, btnPlayVideo, btnReadTextFile, btnOnOffLight;
    Button btnSelectContact, btnManageCallLog, btnCreateNotification, btnGetUniqueID;

    private static final int CONTACT_PICKER_RESULT = 1001;
    public static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlaySound = (Button)findViewById(R.id.btnPlaySound);
        btnPreference = (Button)findViewById(R.id.btnPreference);
        btnLoadImage = (Button)findViewById(R.id.btnLoadImage);
        btnPlayVideo = (Button)findViewById(R.id.btnPlayVideo);
        btnReadTextFile = (Button)findViewById(R.id.btnReadTextFile);
        btnOnOffLight = (Button)findViewById(R.id.btnOnOffLight);
        btnSelectContact = (Button)findViewById(R.id.btnSelectContact);
        btnManageCallLog = (Button)findViewById(R.id.btnManageCallLog);
        btnCreateNotification = (Button)findViewById(R.id.btnCreateNotification);
        btnGetUniqueID = (Button)findViewById(R.id.btnGetUniqueID);

        btnPlaySound.setOnClickListener(this);
        btnPreference.setOnClickListener(this);
        btnLoadImage.setOnClickListener(this);
        btnPlayVideo.setOnClickListener(this);
        btnReadTextFile.setOnClickListener(this);
        btnOnOffLight.setOnClickListener(this);
        btnSelectContact.setOnClickListener(this);
        btnManageCallLog.setOnClickListener(this);
        btnCreateNotification.setOnClickListener(this);
        btnGetUniqueID.setOnClickListener(this);
    }

    @Override
    public void onClick(View button) {
        if(button.getId() == btnPlaySound.getId()){
            SoundPlayer soundPlayer = new SoundPlayer(this);
            soundPlayer.PlayCheeringSound();
        }
        else if(button.getId() == btnPreference.getId()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if(button.getId() == btnLoadImage.getId()){
            Intent intent = new Intent(this, LoadImageActivity.class);
            startActivity(intent);
        }
        else if(button.getId() == btnPlayVideo.getId()){
            Intent intent = new Intent(this, PlayVideoActivity.class);
            startActivity(intent);
        }
        else if(button.getId() == btnReadTextFile.getId()){
            Intent intent = new Intent(this, ReadTextFileActivity.class);
            startActivity(intent);
        }
        else if(button.getId() == btnOnOffLight.getId()){
            Intent intent = new Intent(this, FlashLightActivity.class);
            startActivity(intent);
        }
        else if(button.getId() == btnSelectContact.getId()){
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, CONTACT_PICKER_RESULT);
        }
        else if(button.getId() == btnManageCallLog.getId()){
            Intent intent = new Intent(this, ManageCallLogActivity.class);
            startActivity(intent);
        }
        else if(button.getId() == btnCreateNotification.getId()){
            ShowNotificationMessage(this, "Sample Notification", "This is a sample notification");
        }
        else if(button.getId() == btnGetUniqueID.getId()){
            String uniqueID = UUID.randomUUID().toString();

            Toast.makeText(this, uniqueID, Toast.LENGTH_LONG).show();
        }
    }

    public void ShowNotificationMessage(Context mContext, final String title, final String message) {
        final int icon = R.drawable.icon;

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(inboxStyle)
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    // handle contact results
                    Uri contactData = data.getData();
                    Cursor cursor =  managedQuery(contactData, null, null, null, null);

                    if (cursor.moveToFirst()) {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = "Not Found";

                        String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if (hasPhone.equalsIgnoreCase("1"))
                            hasPhone = "true";
                        else
                            hasPhone = "false";

                        if (Boolean.parseBoolean(hasPhone)) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = " + contactId, null, null);
                            if (phones != null) {
                                while (phones.moveToNext()) {
                                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                }

                                phones.close();
                            }
                        }

                        if(phoneNumber != "Not Found")
                        {
                            Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this, "The selected contact doesn't have a phone number.", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
            }
        } else {
            // gracefully handle failure
            Toast.makeText(this, "Contact Not Selected", Toast.LENGTH_LONG).show();
        }
    }
}
