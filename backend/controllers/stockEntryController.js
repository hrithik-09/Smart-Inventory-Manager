const { StockEntry, Product } = require("../models");

// Create Stock Entry
exports.createStockEntry = async (req, res) => {
  try {
    const {productId,quantity} = req.body;
    const stockEntry = await StockEntry.create(req.body);
    const product = await Product.findByPk(productId);
    product.quantity+=quantity;
    await product.save();

    res.status(201).json(stockEntry);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Get All Stock Entries
exports.getAllStockEntries = async (req, res) => {
  try {
    const entries = await StockEntry.findAll({ include: Product });
    res.json(entries);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// Get Stock Entry by ID
exports.getStockEntryById = async (req, res) => {
  try {
    const entry = await StockEntry.findByPk(req.params.id, { include: Product });
    if (!entry) return res.status(404).json({ error: "Stock Entry not found" });
    res.json(entry);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// Update Stock Entry
exports.updateStockEntry = async (req, res) => {
  try {
    const id =req.params.id;
    const {productId,quantity} = req.body;
    const entry = await StockEntry.findByPk(id);
    if (!entry) {
      return res.status(404).json({ error: "Stock Entry not found" });
    }
     
    const oldProduct = await Product.findByPk(entry.productId);
    oldProduct.quantity-=entry.quantity;
    await oldProduct.save();

    const newProduct = await Product.findByPk(productId)
    newProduct.quantity+=quantity;
    await newProduct.save();

    await entry.update(req.body);

    res.json(entry);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Delete Stock Entry
exports.deleteStockEntry = async (req, res) => {
  try {
    const id = req.params.id;
    const entry = await StockEntry.findByPk(id);
    if (!entry) {
      return res.status(404).json({ error: "Stock Entry not found" });
    }
     
    const oldProduct = await Product.findByPk(entry.productId);
    oldProduct.quantity-=entry.quantity;
    await oldProduct.save();

    await entry.destroy();

    res.json({ message: "Stock Entry deleted" });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
