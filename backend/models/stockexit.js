'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class StockExit extends Model {
    static associate(models) {
      StockExit.belongsTo(models.Product, { foreignKey: 'productId' });
    }
  }
  StockExit.init({
    productId: DataTypes.INTEGER,
    quantity: DataTypes.INTEGER,
    date: DataTypes.DATE,
    notes: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'StockExit',
  });
  return StockExit;
};