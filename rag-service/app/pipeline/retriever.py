from langchain_community.vectorstores import FAISS
from typing import List

class DocumentRetriever:
    def __init__(self, vector_store: FAISS):
        self.vector_store = vector_store

    def retrieve_relevant_chunks(self, query: str, k: int = 5) -> List[str]:
        """
        Retrieves top-k relevant chunks for a given query.
        """
        docs = self.vector_store.similarity_search(query, k=k)
        return [doc.page_content for doc in docs]
