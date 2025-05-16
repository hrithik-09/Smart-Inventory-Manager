const { StockExit, Product } = require("../models");

// Create Stock Exit
exports.createStockExit = async (req, res) => {
  try {
    const stockExit = await StockExit.create(req.body);
    res.status(201).json(stockExit);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Get All Stock Exits
exports.getAllStockExits = async (req, res) => {
  try {
    const exits = await StockExit.findAll({ include: Product });
    res.json(exits);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// Get Stock Exit by ID
exports.getStockExitById = async (req, res) => {
  try {
    const exit = await StockExit.findByPk(req.params.id, { include: Product });
    if (!exit) return res.status(404).json({ error: "Stock Exit not found" });
    res.json(exit);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// Update Stock Exit
exports.updateStockExit = async (req, res) => {
  try {
    const [updated] = await StockExit.update(req.body, {
      where: { id: req.params.id },
    });
    if (!updated) return res.status(404).json({ error: "Stock Exit not found" });
    const updatedExit = await StockExit.findByPk(req.params.id);
    res.json(updatedExit);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Delete Stock Exit
exports.deleteStockExit = async (req, res) => {
  try {
    const deleted = await StockExit.destroy({ where: { id: req.params.id } });
    if (!deleted) return res.status(404).json({ error: "Stock Exit not found" });
    res.json({ message: "Stock Exit deleted" });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
