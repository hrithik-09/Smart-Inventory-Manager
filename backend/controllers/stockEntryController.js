const { StockEntry, Product } = require("../models");

// Create Stock Entry
exports.createStockEntry = async (req, res) => {
  try {
    const stockEntry = await StockEntry.create(req.body);
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
    const [updated] = await StockEntry.update(req.body, {
      where: { id: req.params.id },
    });
    if (!updated) return res.status(404).json({ error: "Stock Entry not found" });
    const updatedEntry = await StockEntry.findByPk(req.params.id);
    res.json(updatedEntry);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Delete Stock Entry
exports.deleteStockEntry = async (req, res) => {
  try {
    const deleted = await StockEntry.destroy({ where: { id: req.params.id } });
    if (!deleted) return res.status(404).json({ error: "Stock Entry not found" });
    res.json({ message: "Stock Entry deleted" });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
