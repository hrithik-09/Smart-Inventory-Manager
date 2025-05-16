const express = require("express");
const app = express();
const userRoutes = require("./routes/userRoutes");
const productRoutes = require("./routes/productRoutes");
const categoryRoutes = require("./routes/categoryRoutes");

app.use(express.json());

app.use("/api/users", userRoutes);
app.use("/api/products",productRoutes);
app.use("/api/categories",categoryRoutes);

module.exports = app;
