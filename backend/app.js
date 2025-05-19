const express = require("express");
const app = express();
const userRoutes = require("./routes/userRoutes");
const productRoutes = require("./routes/productRoutes");
const categoryRoutes = require("./routes/categoryRoutes");
const stockEntryRoutes = require("./routes/stockEntryRoutes");
const supplierRoutes = require("./routes/supplierRoutes");
const stockExitRoutes = require("./routes/stockExitRoutes");
const authRoutes = require('./routes/authRoutes');
const authenticateToken = require('./middlewares/authMiddleware');
app.use(express.json());
app.use('/api/auth', authRoutes);
app.use(authenticateToken);

app.use("/api/users", userRoutes);
app.use("/api/products",productRoutes);
app.use("/api/categories",categoryRoutes);
app.use("/api/stockEntries",stockEntryRoutes);
app.use("/api/suppliers", supplierRoutes);
app.use("/api/stockExits", stockExitRoutes);

module.exports = app;
