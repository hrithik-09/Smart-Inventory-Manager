'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class StockEntry extends Model {
    static associate(models) {
      StockEntry.belongsTo(models.Product, { foreignKey: 'productId' });
    }
  }
  StockEntry.init({
    productId: DataTypes.INTEGER,
    quantity: DataTypes.INTEGER,
    date: DataTypes.DATE,
    notes: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'StockEntry',
  });
  return StockEntry;
};