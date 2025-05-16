const { Supplier } = require("../models");

// Create Supplier
exports.createSupplier = async (req, res) => {
  try {
    const supplier = await Supplier.create(req.body);
    res.status(201).json(supplier);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Get All Suppliers
exports.getAllSuppliers = async (req, res) => {
  try {
    const suppliers = await Supplier.findAll();
    res.json(suppliers);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// Get Supplier by ID
exports.getSupplierById = async (req, res) => {
  try {
    const supplier = await Supplier.findByPk(req.params.id);
    if (!supplier) return res.status(404).json({ error: "Supplier not found" });
    res.json(supplier);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// Update Supplier
exports.updateSupplier = async (req, res) => {
  try {
    const [updated] = await Supplier.update(req.body, {
      where: { id: req.params.id },
    });
    if (!updated) return res.status(404).json({ error: "Supplier not found" });
    const updatedSupplier = await Supplier.findByPk(req.params.id);
    res.json(updatedSupplier);
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};

// Delete Supplier
exports.deleteSupplier = async (req, res) => {
  try {
    const deleted = await Supplier.destroy({
      where: { id: req.params.id },
    });
    if (!deleted) return res.status(404).json({ error: "Supplier not found" });
    res.json({ message: "Supplier deleted" });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
