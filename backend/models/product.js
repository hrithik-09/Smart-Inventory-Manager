'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Product extends Model {
    static associate(models) {
      Product.belongsTo(models.Category, { foreignKey: 'categoryId' });
      Product.belongsTo(models.Supplier, { foreignKey: 'supplierId' });
      Product.hasMany(models.StockEntry, { foreignKey: 'productId' });
      Product.hasMany(models.StockExit, { foreignKey: 'productId' });
    }
  }
  Product.init({
    name: DataTypes.STRING,
    description: DataTypes.STRING,
    quantity: DataTypes.INTEGER,
    price: DataTypes.FLOAT,
    categoryId: DataTypes.INTEGER,
    supplierId: DataTypes.INTEGER,
    threshold: DataTypes.INTEGER,

  }, {
    sequelize,
    modelName: 'Product',
  });
  return Product;
};