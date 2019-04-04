package com.example.jogodavelha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int vitoriasO = 0;
    int vitoriasX = 0;
    int empate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Jogo jogo = new Jogo(new VitoriaCallback() {
            @Override
            public void run(Jogo.Status vencedor) {
                if(vencedor == Jogo.Status.CROSS){
                    vitoriasX += 1;
                }
                else if(vencedor == Jogo.Status.CIRCLE){
                    vitoriasO += 1;
                }
                else{
                    empate += 1;
                }
                ((TextView)findViewById(R.id.textView)).setText(
                        "X: " + Integer.toString(vitoriasX) + "\n" +
                        "O: " + Integer.toString(vitoriasO) + "\n" +
                        "-: " + Integer.toString(empate)
                );
                LimpaBotoes();
            }
        });
        AddListeners(jogo);


    }

    private void LimpaBotoes(){
        for (Integer i =0; i<9; i++){
            final int pos = i;
            final Button btn = (Button)findViewById(getResources().getIdentifier("button" + i.toString(), "id", getPackageName()));
            btn.setText("");
        }
    }

    private void AddListeners(final Jogo jogo){
        for (Integer i =0; i<9; i++){
            final int pos = i;
            final Button btn = (Button)findViewById(getResources().getIdentifier("button" + i.toString(), "id", getPackageName()));
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Jogo.Status status = jogo.ProcessaJogada(pos);
                    if (status == Jogo.Status.CIRCLE) {
                        btn.setText("O");
                    }
                    else if (status == Jogo.Status.CROSS) {
                        btn.setText("X");
                    }
                }
            });
        }
    }
}
