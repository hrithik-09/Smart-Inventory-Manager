const { User } = require('../models');
const jwt = require('jsonwebtoken');
require('dotenv').config();
const generateToken = (user) => {
  return jwt.sign(
    { id: user.id, email: user.email, role: user.role },
    process.env.JWT_SECRET,
    { expiresIn: '1h' }
  );
};

// Signup
exports.signup = async (req, res) => {
  try {
    const user = await User.create(req.body); 
     res.status(201).json({ success: true, message: 'User created successfully'});
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
};


// POST /api/auth/login
exports.login = async (req, res) => {
  try {
    const { email, password } = req.body;

    const user = await User.findOne({ where: { email } });
    if (!user) return res.status(401).json({ error: 'Invalid email or password' });

    const isValid = await user.validPassword(password);
    if (!isValid) return res.status(401).json({ error: 'Invalid email or password' });

    const token = generateToken(user);
    res.json({ token, user });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

// GET /api/auth/validate
exports.validateToken = async (req, res) => {
  try {
    res.status(200).json({ message: 'Token is valid' });
  } catch (err) {
    res.status(401).json({ error: 'Invalid token' });
  }
};

