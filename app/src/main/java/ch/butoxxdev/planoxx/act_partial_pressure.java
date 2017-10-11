package ch.butoxxdev.planoxx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.String;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class act_partial_pressure extends Activity {

    private ImageButton ibtBack;
    private EditText txbProf;
    private EditText txbPrct;
    private EditText txbPp;
    private Button btnCalcul;
    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_partial_pressure);

        //Init View
        ibtBack = (ImageButton) findViewById(R.id.ibtBack);
        txbProf = (EditText) findViewById(R.id.txbProf);
        txbPrct = (EditText) findViewById(R.id.txbPrct);
        txbPp = (EditText) findViewById(R.id.txbPp);
        btnCalcul = (Button) findViewById(R.id.btnCalcul);
        txvResult = (TextView) findViewById(R.id.txvResult);

        // Init Listeners
        ibtBack.setOnClickListener(ibtBackClick);
        btnCalcul.setOnClickListener(btnCalculClick);
    }

    private View.OnClickListener ibtBackClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // Click code
            act_partial_pressure.this.finish();
        }
    };

    private View.OnClickListener btnCalculClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // Click code
            double dblPrct = 0;
            double dblProf = 0;
            double dblPp = 0;
            txvResult.setText("Resultat: ");

            if(Double.parseDouble(txbPrct.getText().toString()) > 0)
            {
                dblPrct = Double.parseDouble(txbPrct.getText().toString());
            }

            if(Double.parseDouble(txbPp.getText().toString()) > 0)
            {
                dblPp = Double.parseDouble(txbPp.getText().toString());
            }

            if(Double.parseDouble(txbProf.getText().toString()) > 0)
            {
                dblProf = Double.parseDouble(txbProf.getText().toString());
            }

            txvResult.setText(txvResult.getText().toString() + Double.toString(f_CalculPressionPartielle(dblProf,dblPp,dblPrct)));
        }
    };

    private double f_RoundToTwoDecimal(double dblNumber)
    {
        double dblResult = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        dblResult = Double.parseDouble(df.format(dblNumber));
        return dblResult;
    };

    private double f_ConvertirPourcentVersPression(double dblPrct)
    {
        double dblResult = -1;

        if(dblPrct >= 1)
        {
            dblResult = dblPrct/100;
        }

        return f_RoundToTwoDecimal(dblResult);
    };

    private double f_ConvertirPressionVersPourcent(double dblPrct)
    {
        double dblResult = -1;

        if(dblPrct <= 1)
        {
            dblResult = dblPrct*100;
        }

        return f_RoundToTwoDecimal(dblResult);
    };

    private double f_ConvertirProfondeurVersPressionAmbiante(double dblProf)
    {
        double dblResult = -1;

        dblResult = (dblProf/10)+1;

        return f_RoundToTwoDecimal(dblResult);
    };

    private double f_ConvertirPressionAmbianteVersProfondeur(double dblPab)
    {
        double dblResult = -1;

        dblResult = (dblPab-1)*10;

        return f_RoundToTwoDecimal(dblResult);
    };

    private double f_CalculPressionPartielle(double dblProf, double dblPp, double dblPrct)
    {
        double dblResult = 0;

        if(dblProf == 0)
        {
            // Calcul pression ambiante
            dblResult = dblPp/f_ConvertirPourcentVersPression(dblPrct);
            dblResult = f_ConvertirPressionAmbianteVersProfondeur(dblResult);
        }
        else if(dblPp == 0)
        {
            // Calcul pression partielle
            dblResult = f_ConvertirPourcentVersPression(dblPrct)*f_ConvertirProfondeurVersPressionAmbiante(dblProf);
        }
        else if(dblPrct == 0)
        {
            // Calcul pourcentage
            dblResult = dblPp/f_ConvertirProfondeurVersPressionAmbiante(dblProf);
            dblResult = f_ConvertirPressionVersPourcent(dblResult);
        }
        return f_RoundToTwoDecimal(dblResult);
    };
}
