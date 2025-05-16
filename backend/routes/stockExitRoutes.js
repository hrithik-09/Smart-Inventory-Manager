const express = require("express");
const router = express.Router();
const stockExitController = require("../controllers/stockExitController");

router.post("/", stockExitController.createStockExit);
router.get("/", stockExitController.getAllStockExits);
router.get("/:id", stockExitController.getStockExitById);
router.put("/:id", stockExitController.updateStockExit);
router.delete("/:id", stockExitController.deleteStockExit);

module.exports = router;
