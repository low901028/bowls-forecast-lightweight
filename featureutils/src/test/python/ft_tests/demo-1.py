import featuretools as ft
if __name__ == '__main__':
    # 基础原始数据
    data = ft.load_mock_customer()
    # 获取原始数据中不同的部分
    # session
    sessions_df = data["sessions"]
    # products
    products_df = data["products"]
    # transactions
    transactions_df = data["transactions"]
    # customers
    customers_df = data["customers"]

    # 创建EntitySet
    es = ft.EntitySet(id="tests")
    # 添加entity
    es = es.entity_from_dataframe(
                entity_id="trans_entity",
                dataframe=transactions_df,
                index="transaction_id",

    )

    es = es.entity_from_dataframe(
                entity_id="session_entity",
                dataframe=sessions_df,
                index="session_id",
    )

    es = es.entity_from_dataframe(
        entity_id="customer_entity",
        dataframe=customers_df,
        index="customer_id",
    )

    es = es.entity_from_dataframe(
        entity_id="product_entity",
        dataframe=products_df,
        index="product_id",
    )

    # 查看对应的entity的variable及其类型
    #print(es["trans_entity"].variables)
    # 输出entity对应的数据
    #print(es["trans_entity"].head(5))

    print(es["trans_entity"].head(5))
    print(es["session_entity"].head(5))
    print(es["customer_entity"].head(5))
    print(es["product_entity"].head(5))

    # 添加数据集间的关联关系
    # trans_sessioin_relationship = ft.Relationship(
    #                     es["trans_entity"]["session_id"],
    #                     es["session_entity"]["session_id"]
    # )
    # es = es.add_relationship(trans_sessioin_relationship)
    #
    # trans_product_relationship = ft.Relationship(
    #     es["trans_entity"]["product_id"],
    #     es["product_entity"]["product_id"]
    # )
    # es = es.add_relationship(trans_product_relationship)
    #
    # session_customer_relationship = ft.Relationship(
    #     es["session_entity"]["customer_id"],
    #     es["customer_entity"]["customer_id"]
    # )
    # es = es.add_relationship(session_customer_relationship)

    # 提取需要属性
    es = es.normalize_entity(
                    base_entity_id="trans_entity",
                    new_entity_id="sessions",
                    index="session_id",
                    #additional_variables=["device", "customer_id", "zip_code"]
    )

    # 标准化

    # 结果
