package com.rkdigital.stocklytics

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import android.graphics.Color
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.rkdigital.stocklytics.databinding.ActivityMainBinding
import com.rkdigital.stocklytics.storage.SharedPreferenceHelper
import com.rkdigital.stocklytics.utils.SharedPrefKeys

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbarAndDrawer()
        setupNavigationDrawer()
        loadUserDetailsToDrawer()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, dashboard())
                .commit()
            binding.navigationView.setCheckedItem(R.id.nav_dashboard)
        }

    }
    private fun setupToolbarAndDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.toolbar.popupTheme = R.style.PopupMenuOverlay

        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close

        ).apply {
            isDrawerIndicatorEnabled = true
        }

        val color = ContextCompat.getColor(this, R.color.green_accent) // or use "#C8FD00"
        binding.toolbar.overflowIcon?.setTint(color)
        val navIcon = AppCompatResources.getDrawable(this, R.drawable.ic_menu)?.apply {
            setTint(color)
        }
        binding.toolbar.navigationIcon = navIcon

        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }
    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        if (menu.javaClass.simpleName == "MenuBuilder") {
            try {
                val m = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", Boolean::class.javaPrimitiveType)
                m.isAccessible = true
                m.invoke(menu, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return super.onMenuOpened(featureId, menu)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            val icon = item.icon
            icon?.mutate()?.setTint(ContextCompat.getColor(this, R.color.green_accent))
            item.icon = icon
        }
        return true
    }
    private fun setupNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, dashboard())
                        .commit()
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_products -> {
                    showToast("Products")
                    // loadFragment(ProductFragment())
                }
                R.id.nav_categories -> {
                    showToast("Categories")
                    // loadFragment(CategoryFragment())
                }
                R.id.nav_suppliers -> {
                    showToast("Suppliers")
                    // loadFragment(SupplierFragment())
                }
                R.id.nav_stock -> {
                    showToast("Stock Movement")
                    // loadFragment(StockFragment())
                }
                R.id.nav_reports -> {
                    showToast("Reports")
                    // loadFragment(ReportFragment())
                }
                R.id.nav_users -> {
                    showToast("User Management")
                    // loadFragment(UserFragment())
                }
                R.id.nav_audit -> {
                    showToast("Audit Logs")
                    // loadFragment(AuditFragment())
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }
    private fun loadUserDetailsToDrawer() {
        val headerView = binding.navigationView.getHeaderView(0)
        val txtEmail = headerView.findViewById<TextView>(R.id.txtUserEmail)
        val chipRole = headerView.findViewById<Chip>(R.id.chipRole)

        val prefs = SharedPreferenceHelper.getInstance(this).getUserPrefs()
        txtEmail.text = prefs.getString(SharedPrefKeys.EMAIL, "user@example.com")
        chipRole.text = prefs.getString(SharedPrefKeys.ROLE, "Admin")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                showSearchDialog()
                true
            }
            R.id.action_notifications -> {
                showToast("Notifications")
                true
            }
            R.id.action_profile -> {
                showToast("Profile clicked")
                true
            }
            R.id.action_settings -> {
                showToast("Settings clicked")
                true
            }
            R.id.action_logout -> {
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSearchDialog() {
        val searchEditText = EditText(this)
        searchEditText.hint = "Search..."

        AlertDialog.Builder(this)
            .setTitle("Search")
            .setView(searchEditText)
            .setPositiveButton("Search") { _, _ ->
                val query = searchEditText.text.toString()
                showToast("Searching for: $query")
                // Trigger search logic
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun logoutUser() {
        val prefsHelper = SharedPreferenceHelper.getInstance(this)
        prefsHelper.clearAll(prefsHelper.getUserPrefs());
        prefsHelper.putString(prefsHelper.getAppPrefs(), SharedPrefKeys.IS_LOGGED_IN,"false")
        startActivity(Intent(this, Login::class.java))
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}


