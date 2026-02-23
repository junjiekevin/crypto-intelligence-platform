from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import FAISS
from typing import List
import os

class DocumentEmbedder:
    def __init__(self, model: str = "text-embedding-3-small"):
        self.embeddings = OpenAIEmbeddings(model=model)

    def create_vector_store(self, chunks: List[str]) -> FAISS:
        """
        Creates a FAISS vector store from text chunks.
        """
        if not chunks:
            raise ValueError("No chunks provided for vector store creation")
            
        return FAISS.from_texts(chunks, self.embeddings)

    def save_vector_store(self, vector_store: FAISS, path: str):
        """
        Saves the vector store to a directory.
        """
        os.makedirs(os.path.dirname(path), exist_ok=True)
        vector_store.save_local(path)

    def load_vector_store(self, path: str) -> FAISS:
        """
        Loads the vector store from a directory.
        """
        if not os.path.exists(path):
            raise FileNotFoundError(f"Vector store not found at: {path}")
            
        return FAISS.load_local(path, self.embeddings, allow_dangerous_deserialization=True)
