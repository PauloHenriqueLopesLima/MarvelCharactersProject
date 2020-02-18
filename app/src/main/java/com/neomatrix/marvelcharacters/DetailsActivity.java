package com.neomatrix.marvelcharacters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neomatrix.marvelcharacters.models.Result;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewDetailsName;
    private TextView textViewDetailsDescription;
    private ImageView imageViewDetailsCharacterPhoto;
    private Result personagem;
    private Button btnHq;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //overridePendingTransition(R.anim.anim_one, R.anim.anim_one);

        btnHq = findViewById(R.id.buttonHq);



        String nome;
        String desc;
        String thumb;
        String ext;

        nome = getIntent().getStringExtra("nome");
        desc = getIntent().getStringExtra("desc");
        thumb = getIntent().getStringExtra("thumb");
        ext = getIntent().getStringExtra("ext");


        textViewDetailsName = findViewById(R.id.textViewDetailsNameCharacter);
        textViewDetailsDescription = findViewById(R.id.textViewDetailsDescriptionCharacter);
        imageViewDetailsCharacterPhoto = findViewById(R.id.imageViewDetailsPhotoCharacter);

        textViewDetailsName.setText(nome);
        textViewDetailsDescription.setText(desc);

        Picasso.get()


                .load(thumb + "." + ext)

                //.resize(170, 170)
                .into(imageViewDetailsCharacterPhoto);

        btnHq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ComicsActivity.class);
                String id = getIntent().getStringExtra("id");
                intent.putExtra("id",id);
                startActivity(intent);

            }
        });
    }
}





