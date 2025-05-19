const { StockExit, Product } = require("../models");

// Create Stock Exit
exports.createStockExit = async (req, res) => {
  try {
    const {productId,quantity} = req.body;
    const product = await Product.findByPk(productId);
    if (product.quantity<quantity) {
      return res.status(400).json({error:"Not enough stock"});
    }

    const stockExit = await StockExit.create(req.body);
    product.quantity-=quantity;
    await product.save();
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
    const id = req.params.id;
    const { productId, quantity } = req.body;

    const exit = await StockExit.findByPk(id);
    if (!exit) return res.status(404).json({ error: "Stock Exit not found" });

    const oldProduct = await Product.findByPk(exit.productId);
    oldProduct.quantity += exit.quantity; 
    await oldProduct.save();

    const newProduct = await Product.findByPk(productId);

    if (newProduct.quantity < quantity) {
      return res.status(400).json({ error: "Not enough stock for update" });
    }

    newProduct.quantity -= quantity;
    await newProduct.save();

    // Update the stock exit
    await exit.update(req.body);

    res.json(exit);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Delete Stock Exit
exports.deleteStockExit = async (req, res) => {
  try {
    const id = req.params.id;
    const exit = await StockExit.findByPk(id);
    if (!exit) return res.status(404).json({ error: "Stock Exit not found" });

    const product = await Product.findByPk(exit.productId);
    product.quantity += exit.quantity; // add back stock on deletion
    await product.save();

    await exit.destroy();
    res.json({ message: "Stock Exit deleted" });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
