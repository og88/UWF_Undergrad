using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Activity2
{
    
    public class Currency
    {
        private CurrencyType type;
    
        public CurrencyType Type
        {
            get;
            set;
        }

        public Currency(CurrencyType type)
        {
            this.type = type;
        }

        public virtual double ConvertCurrency(double startingAmount, CurrencyType convertedCurrencyType)
        {
            double finalAmount = 0.0;

            switch (this.type)
            {
                case CurrencyType.USD:
                    switch (convertedCurrencyType)
                    {
                        case CurrencyType.USD:
                            finalAmount = startingAmount;
                            break;
                        case CurrencyType.Pounds:
                            finalAmount = 0.65 * startingAmount;
                            break;
                        case CurrencyType.Euros:
                            finalAmount = 0.88 * startingAmount;
                            break;
                    }
                    break;
                case CurrencyType.Pounds:
                    switch (convertedCurrencyType)
                    {
                        case CurrencyType.USD:
                            finalAmount = 1.54 * startingAmount;
                            break;
                        case CurrencyType.Pounds:
                            finalAmount = startingAmount;
                            break;
                        case CurrencyType.Euros:
                            finalAmount = 1.35 * startingAmount;
                            break;
                    }
                    break;
                case CurrencyType.Euros:
                    switch (convertedCurrencyType)
                    {
                        case CurrencyType.USD:
                            finalAmount = 1.14 * startingAmount;
                            break;
                        case CurrencyType.Pounds:
                            finalAmount = 0.74 * startingAmount;
                            break;
                        case CurrencyType.Euros:
                            finalAmount = startingAmount;
                            break;
                    }
                    break;
            }

            return finalAmount;
        }

        //formats a currency to two decimal places
        public virtual String formatCurrency(double amount)
        {
            return String.Format("{0:0.00}", amount);
        }   
}

}
