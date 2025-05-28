package com.example.sensordhtt22

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.*

class MainActivity : ComponentActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var ledRef: DatabaseReference
    private lateinit var tempRef: DatabaseReference
    private lateinit var humRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance("https://cambiennhiet-2b35b-default-rtdb.firebaseio.com/")
        database.setPersistenceEnabled(true)
        ledRef = database.getReference("Led/status")
        tempRef = database.getReference("Sensor/temperature")
        humRef = database.getReference("Sensor/humidity")

        setContent {
            AtmosSenseApp()
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    @Composable
    fun AtmosSenseApp() {
        var temperature by remember { mutableStateOf("--") }
        var humidity by remember { mutableStateOf("--") }
        var ledStatus by remember { mutableStateOf(false) }
        var isConnected by remember { mutableStateOf(false) }

        val gradient = Brush.verticalGradient(
            colors = listOf(Color(0xFFF8FAFC), Color(0xFFE2E8F0))
        )

        LaunchedEffect(true) {
            isConnected = isNetworkConnected(this@MainActivity)

            tempRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    temperature = snapshot.getValue()?.toString() ?: "--"
                }

                override fun onCancelled(error: DatabaseError) {
                    temperature = "Error"
                }
            })

            humRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    humidity = snapshot.getValue()?.toString() ?: "--"
                }

                override fun onCancelled(error: DatabaseError) {
                    humidity = "Error"
                }
            })

            ledRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ledStatus = snapshot.getValue(Boolean::class.java) ?: false
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        Box(modifier = Modifier.fillMaxSize().background(gradient)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderCard(isConnected)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SensorCard(
                        modifier = Modifier.weight(1f),
                        title = "Temperature",
                        value = temperature,
                        unit = "°C",
                        icon = "🔥",
                        color = Color(0xFFEF4444)
                    )
                    SensorCard(
                        modifier = Modifier.weight(1f),
                        title = "Humidity",
                        value = humidity,
                        unit = "%",
                        icon = "🌫️",
                        color = Color(0xFF06B6D4)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                LedControlCard(ledStatus = ledStatus) {
                    println("🟡 Gạt công tắc! Trạng thái hiện tại: $ledStatus")
                    val newStatus = !ledStatus
                    ledRef.setValue(newStatus)
                        .addOnSuccessListener {
                            println("✅ Firebase cập nhật thành công!")
                            ledStatus = newStatus
                        }
                        .addOnFailureListener {
                            println("❌ Lỗi khi gửi lên Firebase: ${it.message}")
                        }
                }




                Spacer(modifier = Modifier.height(24.dp))
                NetworkStatusCard(isConnected)
                Spacer(modifier = Modifier.weight(1f))
                FooterText()
            }
        }
    }

    @Composable
    fun HeaderCard(isConnected: Boolean) {
        Card(
            modifier = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🌍 AtmosSense", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text("Environment Monitor", fontSize = 16.sp, color = Color(0xFF64748B))
            }
        }
    }

    @Composable
    fun NetworkStatusCard(isConnected: Boolean) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📶 Connection", fontWeight = FontWeight.Medium, color = Color.Gray)
                    Text(
                        text = if (isConnected) "Online" else "Offline",
                        fontWeight = FontWeight.Bold,
                        color = if (isConnected) Color(0xFF10B981) else Color(0xFFEF4444)
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📡 Signal Strength", fontWeight = FontWeight.Medium, color = Color.Gray)
                    Text("Excellent (100%)", fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                }
            }
        }
    }

    @Composable
    fun SensorCard(
        modifier: Modifier = Modifier,
        title: String,
        value: String,
        unit: String,
        icon: String,
        color: Color
    ) {
        Card(
            modifier = modifier.shadow(3.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = icon, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(title, fontSize = 14.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = color)
                    Text(unit, fontSize = 14.sp, color = Color(0xFF64748B), modifier = Modifier.padding(start = 2.dp, bottom = 4.dp))
                }
            }
        }
    }

    @Composable
    fun LedControlCard(ledStatus: Boolean, onToggle: () -> Unit) {
        val ledColor by animateColorAsState(
            targetValue = if (ledStatus) Color(0xFFFBBF24) else Color(0xFF9CA3AF),
            animationSpec = tween(300),
            label = "led_color"
        )
        Card(
            modifier = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = if (ledStatus) Color(0xFFFEF3C7) else Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "💡", fontSize = 32.sp, color = ledColor)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("LED Control", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E293B))
                        Text(if (ledStatus) "ON" else "OFF", fontSize = 14.sp, color = ledColor, fontWeight = FontWeight.Bold)
                    }
                }
                Switch(
                    checked = ledStatus,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFFFBBF24),
                        checkedTrackColor = Color(0xFFFBBF24).copy(alpha = 0.3f),
                        uncheckedThumbColor = Color(0xFF9CA3AF),
                        uncheckedTrackColor = Color(0xFF9CA3AF).copy(alpha = 0.3f)
                    )
                )
            }
        }
    }

    @Composable
    fun FooterText() {
        Text(
            text = "© 2025 AtmosSense Project",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF64748B),
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )
    }
}
