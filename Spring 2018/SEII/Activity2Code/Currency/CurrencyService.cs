using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Activity2
{
    public class CurrencyService
    {
        private ICurrencyRepository repository;

        public ICurrencyRepository Repository
        {
            get;
            set;
        }

        public CurrencyService(ICurrencyRepository repo)
        {
            this.repository = repo;
        }

        public double convertCurrency(double amount, CurrencyType startingType, CurrencyType endingType)
        {
            Currency currency = this.repository.getCurrency(startingType);
            return currency.ConvertCurrency(amount, endingType);
        }
    }
}
