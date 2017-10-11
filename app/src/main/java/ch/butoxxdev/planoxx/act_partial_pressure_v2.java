package ch.butoxxdev.planoxx;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by bende on 09.10.2017.
 */

public class act_partial_pressure_v2 extends Activity
{
    private ImageButton ibtBack;
    private EditText txbProf;
    private EditText txbPrct;
    private EditText txbPp;
    private Button btnCalcul;
    private RadioButton btrO2;
    private RadioButton btrN2;
    private TextView txvPrct;
    private TextView txvPp;
    private Button btnResetCalcul;
    private Button btnResetAll;
    private TextView txvDetails;
    private int ga_intLastCalculed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_partial_pressure_v2);

        //Init View
        ibtBack = (ImageButton) findViewById(R.id.ibtBack);
        txbProf = (EditText) findViewById(R.id.txbProf);
        txbPrct = (EditText) findViewById(R.id.txbPrct);
        txbPp = (EditText) findViewById(R.id.txbPp);
        btnCalcul = (Button) findViewById(R.id.btnCalcul);
        btrO2 = (RadioButton) findViewById(R.id.btrO2);
        btrN2 = (RadioButton) findViewById(R.id.btrN2);
        txvPrct = (TextView) findViewById(R.id.txvPrct);
        txvPp = (TextView) findViewById(R.id.txvPp);
        btnResetCalcul = (Button) findViewById(R.id.btnResetCalcul);
        btnResetAll = (Button) findViewById(R.id.btnResetAll);
        txvDetails = (TextView) findViewById(R.id.txvDetails);

        // Init Listeners
        ibtBack.setOnClickListener(ibtBackClick);
        txbPrct.setOnFocusChangeListener(txbPrctFocus);
        txbProf.setOnFocusChangeListener(txbProfFocus);
        txbPp.setOnFocusChangeListener(txbPpFocus);
        btnCalcul.setOnClickListener(btnCalculClick);
        btrO2.setOnClickListener(btrO2Click);
        btrN2.setOnClickListener(btrN2Click);
        btnResetCalcul.setOnClickListener(btnResetCalculClick);
        btnResetAll.setOnClickListener(btnResetAllClick);
    }

    private View.OnClickListener ibtBackClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // Click code
            act_partial_pressure_v2.this.finish();
        }
    };

    private View.OnFocusChangeListener txbPrctFocus = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View view, boolean hasFocus)
        {
            if(hasFocus)
            {
                if(txbPrct.getText().toString().trim().equals("0"))
                {
                    txbPrct.setText("");
                }
            }
            else
            {
                if(txbPrct.getText().toString().trim().isEmpty())
                {
                    txbPrct.setText(R.string.DefaultTxbNumber);
                }
            }
        }
    };

    private View.OnFocusChangeListener txbProfFocus = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View view, boolean hasFocus)
        {
            if(hasFocus)
            {
                if(txbProf.getText().toString().trim().equals("0"))
                {
                    txbProf.setText("");
                }
            }
            else
            {
                if(txbProf.getText().toString().trim().isEmpty())
                {
                    txbProf.setText(R.string.DefaultTxbNumber);
                }
            }
        }
    };

    private View.OnFocusChangeListener txbPpFocus = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View view, boolean hasFocus)
        {
            if(hasFocus)
            {
                if(txbPp.getText().toString().trim().equals("0"))
                {
                    txbPp.setText("");
                }
            }
            else
            {
                if(txbPp.getText().toString().trim().isEmpty())
                {
                    txbPp.setText(R.string.DefaultTxbNumber);
                }
            }
        }
    };

    private View.OnClickListener btnCalculClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // Click code
            if(txbPp.getText().toString().isEmpty())
            {
                txbPp.setText(R.string.DefaultTxbNumber);
            }
            if(txbPrct.getText().toString().isEmpty())
            {
                txbPrct.setText(R.string.DefaultTxbNumber);
            }
            if(txbProf.getText().toString().isEmpty())
            {
                txbProf.setText(R.string.DefaultTxbNumber);
            }
            double dblPrct = Double.parseDouble(txbPrct.getText().toString());
            double dblProf = Double.parseDouble((txbProf.getText().toString()));
            double dblPp = Double.parseDouble(txbPp.getText().toString());

            if(dblPrct == 0)
            {
                ga_intLastCalculed = 1;
                dblProf = f_ConvertirProfondeurVersPressionAmbiante(dblProf);
                dblPrct = f_RoundToTwoDecimal(f_ConvertirPressionVersPourcent(f_CalculPourcentage(dblProf,dblPp)));
                txbPrct.setText(Double.toString(dblPrct));
                txvDetails.setText("Explications:" + "\n1. Convertir la prof. en PAB : (Prof./10)+1\n2.Trouver la pression : PP/PAB\n3.Convertir la pression en % : Pres*100");
            }
            else if(dblProf == 0)
            {
                ga_intLastCalculed = 2;
                dblPrct = f_ConvertirPourcentVersPression(dblPrct);
                dblProf = f_RoundToTwoDecimal(f_ConvertirPressionAmbianteVersProfondeur(f_CalculPressionAmbiante(dblPp,dblPrct)));
                txbProf.setText(Double.toString(dblProf));
                txvDetails.setText("Explications:" + "\n1. Convertir le % en pression : %/100\n2.Trouver la PAB : PP/Pres\n3.Convertir la PAB en Prof. : (PAB-1)*10");
            }
            else if(dblPp == 0)
            {
                ga_intLastCalculed = 3;
                dblPrct = f_ConvertirPourcentVersPression(dblPrct);
                dblProf = f_ConvertirProfondeurVersPressionAmbiante(dblProf);
                dblPp = f_RoundToTwoDecimal(f_CalculPressionPartielle(dblPrct,dblProf));
                txbPp.setText(Double.toString(dblPp));
                txvDetails.setText("Explications:" + "\n1. Convertir la prof. en PAB : (Prof./10)+1\n2. Convertir le % en pression : %/100\n3.Trouver la Pp : Pres*PAB");
            }
        }
    };

    private View.OnClickListener btrO2Click = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(btrO2.isChecked())
            {
                btrN2.setChecked(false);
                f_ChangeTextLayout();
            }
        }
    };

    private View.OnClickListener btrN2Click = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(btrN2.isChecked())
            {
                btrO2.setChecked(false);
                f_ChangeTextLayout();
            }
        }
    };

    private View.OnClickListener btnResetCalculClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // Click code
            switch(ga_intLastCalculed)
            {
                case 1:
                    txbPrct.setText(R.string.DefaultTxbNumber);
                    break;
                case 2:
                    txbProf.setText(R.string.DefaultTxbNumber);
                    break;
                case 3:
                    txbPp.setText(R.string.DefaultTxbNumber);
                    break;
                default:
            }
            txvDetails.setText(R.string.Explications);
        }
    };

    private View.OnClickListener btnResetAllClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            // Click code
            txbPrct.setText(R.string.DefaultTxbNumber);
            txbPp.setText(R.string.DefaultTxbNumber);
            txbProf.setText(R.string.DefaultTxbNumber);
            ga_intLastCalculed = 0;
            txvDetails.setText(R.string.Explications);
        }
    };

    private double f_CalculPressionAmbiante(double dblPp, double dblPrct)
    {
        // Calcul de la PAB
        double dblResult = 0;
        dblResult = dblPp/dblPrct;
        return dblResult;
    };

    private double f_CalculPressionPartielle(double dblPrct, double dblProf)
    {
        double dblResult = 0;
        dblResult = dblPrct*dblProf;
        return dblResult;
    };

    private double f_CalculPourcentage(double dblProf, double dblPp)
    {
        double dblResult = 0;
        dblResult = dblPp/dblProf;
        return dblResult;
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

        return dblResult;
    };

    private double f_ConvertirPressionVersPourcent(double dblPrct)
    {
        double dblResult = -1;

        if(dblPrct <= 1)
        {
            dblResult = dblPrct*100;
        }

        return dblResult;
    };

    private double f_ConvertirProfondeurVersPressionAmbiante(double dblProf)
    {
        double dblResult = -1;

        dblResult = (dblProf/10)+1;

        return dblResult;
    };

    private double f_ConvertirPressionAmbianteVersProfondeur(double dblPab)
    {
        double dblResult = -1;

        dblResult = (dblPab-1)*10;

        return dblResult;
    }

    private void f_ChangeTextLayout()
    {
        if(btrO2.isChecked())
        {
            txvPrct.setText(R.string.PourcentO2);
            txvPp.setText(R.string.PPO2);
        }
        else if(btrN2.isChecked())
        {
            txvPrct.setText(R.string.PourcentN2);
            txvPp.setText(R.string.PPN2);
        }
    }
}
