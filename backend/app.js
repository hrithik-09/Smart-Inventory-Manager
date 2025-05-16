const express = require("express");
const app = express();
const userRoutes = require("./routes/userRoutes");
const productRoutes = require("./routes/productRoutes");
const categoryRoutes = require("./routes/categoryRoutes");
const stockEntryRoutes = require("./routes/stockEntryRoutes");

app.use(express.json());

app.use("/api/users", userRoutes);
app.use("/api/products",productRoutes);
app.use("/api/categories",categoryRoutes);
app.use("/api/stockEntries",stockEntryRoutes);

module.exports = app;
