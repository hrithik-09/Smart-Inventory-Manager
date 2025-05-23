const express = require("express");
const cors = require('cors');
const app = express();
const userRoutes = require("./routes/userRoutes");
const productRoutes = require("./routes/productRoutes");
const categoryRoutes = require("./routes/categoryRoutes");
const stockEntryRoutes = require("./routes/stockEntryRoutes");
const supplierRoutes = require("./routes/supplierRoutes");
const stockExitRoutes = require("./routes/stockExitRoutes");
const authRoutes = require('./routes/authRoutes');
const authenticateToken = require('./middleware/authMiddleware');

app.use(express.json());
app.use(cors());
app.use('/api/auth', authRoutes);
app.use("/api/users", userRoutes);
app.use(authenticateToken);

app.use("/api/products",productRoutes);
app.use("/api/categories",categoryRoutes);
app.use("/api/stockEntries",stockEntryRoutes);
app.use("/api/suppliers", supplierRoutes);
app.use("/api/stockExits", stockExitRoutes);

module.exports = app;
