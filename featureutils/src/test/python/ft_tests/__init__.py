from featuretools.demo.mock_customer import load_mock_customer
import featuretools as ft
if __name__ == '__main__':
    datas = load_mock_customer()
    #print(datas)
    # customers
    customers_df = datas["customers"]
    #print(customers_df)
    # sessions
    sessions_df = datas["sessions"]
    #print(sessions_df.sample(10))
    # transactions
    transactions_df = datas["transactions"]
    #print(transactions_df.sample(10))
    # 构建entityset
    entities = {
        "customers" : (customers_df, "customer_id"),
        "sessions" : (sessions_df, "session_id", "session_start"),
        "transactions" : (transactions_df, "transaction_id", "transaction_time")
    }
    print(entities)
    # 构建entity间的关系
    relationships = [("sessions", "session_id", "transactions", "session_id"),
                     ("customers", "customer_id", "sessions", "customer_id")]

    es = ft.entityset.entityset.EntitySet(id="transactions")
    print(es)