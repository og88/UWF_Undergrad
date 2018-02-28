using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Activity_2;

namespace CurrencyTest
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestToSeeIfAEuroWillBeConvertedToaUSDollar()
        {
            //Arrange 
            var sut = new Currency(CurrencyType.Euros);
            //Act
            double newCurrency = sut.ConvertCurrency(1, CurrencyType.USD);
            //
            Assert.AreEqual(1.14, newCurrency);
        }

        [TestMethod]
        public void TestToSeeIfaUSDollarWillBeConvertedToaPound()
        {
            //Arrange 
            var sut = new Currency(CurrencyType.USD);
            //Act
            double newCurrency = sut.ConvertCurrency(1, CurrencyType.Pounds);
            //
            Assert.AreEqual(0.65, newCurrency);
        }

        [TestMethod]
        public void TestToSeeIfAPoundWillBeConvertedToaUSDollar()
        {
            //Arrange 
            var sut = new Currency(CurrencyType.Pounds);
            //Act
            double newCurrency = sut.ConvertCurrency(1, CurrencyType.USD);
            //
            Assert.AreEqual(1.54, newCurrency);
        }
    }
}
