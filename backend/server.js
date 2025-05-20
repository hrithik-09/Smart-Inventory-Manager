require("dotenv").config();

const app = require("./app");

const PORT = process.env.PORT || 3000;


const runMigrations = async () => {
  const { exec } = require('child_process');
  exec('npx sequelize-cli db:migrate', (error, stdout, stderr) => {
    if (error) {
      console.error('Migration failed:', stderr);
    } else {
      console.log('Migration output:\n', stdout);
    }
  });
};


runMigrations();


app.listen(PORT, () => {
  console.log(`Server running on ${PORT}`);
});
