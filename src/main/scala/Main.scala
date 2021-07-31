import com.snowflake.snowpark._
import com.snowflake.snowpark.functions._
import com.typesafe.config._
import jdk.internal.org.jline.keymap.KeyMap.display

object Main {
  def main(args: Array[String]): Unit = {
    // Replace the <placeholders> below.
    val props = ConfigFactory.load()
    val password = props.getString("dev.password")

    val configs = Map (
      "URL" -> "https://bh83160.ca-central-1.aws.snowflakecomputing.com:443",
      "USER" -> "techwithfru",
      "PASSWORD" -> password ,
      "ROLE" -> "ACCOUNTADMIN",
      "WAREHOUSE" -> "COMPUTE_WH",
      "DB" -> "SNOWFLAKE_SAMPLE_DATA",
      "SCHEMA" -> "TPCH_SF1"
    )
    val session = Session.builder.configs(configs).create
    //session.sql("show tables").show()

    val df = session.read.table("CUSTOMER")
    val dfFilter = df.filter(column("C_NATIONKEY")===24)
    //dfFilter.show()

    val df_CUSTOMER = session.read.table(name = "CUSTOMER")
    val df_ORDERS = session.read.table(name = "ORDERS")
    val df_LINEITEM = session.read.table(name = "LINEITEM")

    val dfJoinedORDERS = df_CUSTOMER.join(df_ORDERS, Seq("o_orderkey"))
    val dfJoinedLO = df_ORDERS.join(df_LINEITEM, df_ORDERS("o_orderkey") === df_LINEITEM("l_orderkey"))

    val dfShipping =dfJoinedLO.select(col("L_SHIPMODE"), col("O_TOTALPRICE")).groupBy("L_SHIPMODE").count()

    dfShipping.show()


  }
}
