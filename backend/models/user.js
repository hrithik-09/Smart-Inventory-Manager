'use strict';
const {Model} = require('sequelize');
const bcrypt = require('bcrypt');
require("dotenv").config();
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    
    static associate(models) {
      
    }
     validPassword(password) {
      return bcrypt.compare(password, this.password);
    }
  }
  User.init({
    name: DataTypes.STRING,
    email: 
    {
      type:DataTypes.STRING,
      allowNull:false,
      unique:true
    },
    password: DataTypes.STRING,
    role: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'User',
   hooks: {
      beforeCreate: async (user) => {
        const salt = await bcrypt.genSalt(parseInt(process.env.SALT_ROUND, 10));
        user.password = await bcrypt.hash(user.password, salt);
      }
    }
  });
  return User;
};