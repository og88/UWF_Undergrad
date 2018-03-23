using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Activity2
{
    
    public abstract class Currency
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
            /*replace sitch statement with if else, to make more safe*/
            if (this.type == CurrencyType.USD)
            {
                /*Create objects from classes created. call that class conversion method*/
                USD us = new USD();
                return us.ConvertCurrency(startingAmount, convertedCurrencyType);
            }
            else if (this.type == CurrencyType.Pounds)
            {
                /*Create both a USD and Pound object. We first convert using the USD object. What is returned is passed onto the second object*/
                Pounds pounds = new Pounds();
                USD us = new USD();
                return pounds.ConvertCurrency(us.ConvertCurrency(startingAmount, convertedCurrencyType), CurrencyType.USD);
            }
            else if (this.type == CurrencyType.Euros)
            {
                /*Create both a USD and Euros object. We first convert using the USD object. What is returned is passed onto the second object*/
                Euros euro = new Euros();
                USD us = new USD();
                return euro.ConvertCurrency(us.ConvertCurrency(startingAmount, convertedCurrencyType), CurrencyType.USD);

            }
            return -1;
                 }

        //formats a currency to two decimal places
        public virtual String formatCurrency(double amount)
        {
            return String.Format("{0:0.00}", amount);
        }   
}

}
