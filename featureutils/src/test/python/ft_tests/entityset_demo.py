import  featuretools as ft
import pandas as pd

if __name__ == '__main__':
    # 加载测试样本数据
    data = ft.load_mock_customer()
    #print(data)
    # customer
    customers_df = data["customers"]
    # session
    sessions_df = data["sessions"]
    # transaction
    transactions_df = data["transactions"]
    #
    products_df = data['products']
    #print(transactions_df.sample(2))

    es = ft.EntitySet(id="transactions-demo")
    es = es.entity_from_dataframe(entity_id="transactions",
                                  dataframe=transactions_df,
                                  index="transaction_id",
                                  time_index="transaction_time",
                                  variable_types={"product_id": ft.variable_types.Categorical})
    #print(es["transactions"].variables)

    es_products = ft.EntitySet(id="products")
    es_products = es_products.entity_from_dataframe(entity_id="products",
                                  dataframe=products_df,
                                  index="product_id",
                                  variable_types={"product_id": ft.variable_types.Categorical})

    #products_df = pd.DataFrame
    #es = es.entity_from_dataframe(entity_id="products",
    #                              dataframe=products_df,
    #                              index="product_id")
    #print(es["products"].variables)
    print(es["transactions"].head(5))

    es = es.normalize_entity(base_entity_id="transactions",
                             new_entity_id="sessions_demo",
                             index="session_id",
                             additional_variables=["transaction_id", "product_id", "amount"])
    #print(es["transactions"].variables)
    #print(es["sessions_demo"].variables)
    #print(es['sessions_demo'].head(5))
    #print(es["transactions"].head(5))


    feature_matrix, feature_defs = ft.dfs(entityset=es,target_entity="transactions-demo")
    print(feature_matrix)